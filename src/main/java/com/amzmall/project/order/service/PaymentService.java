package com.amzmall.project.order.service;

import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.order.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.util.advice.ExMessage;
import com.amzmall.project.order.domain.dto.PaymentDto;
import com.amzmall.project.order.domain.dto.PaymentFailDto;
import com.amzmall.project.order.domain.dto.OrderReqDto;
import com.amzmall.project.order.domain.dto.PaymentResCardDto;
import com.amzmall.project.order.domain.dto.OrderResDto;
import com.amzmall.project.order.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.order.domain.dto.TossErrorDto;
import com.amzmall.project.order.config.TossPaymentConfig;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.users.repository.UsersRepository;
import com.amzmall.project.order.repository.OrderRepository;
import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResDto requestOrder(OrderReqDto orderReqDto){
        int productId = orderReqDto.getProductId();
        String customerEmail = orderReqDto.getCustomerEmail();

        OrderResDto orderResDto;
        try {
            Order order = orderReqDto.toEntity();
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("해당 상품은 존재하지 않습니다."));

            int currentStock = product.getStockQuantity();
            if (currentStock < 5) {
                throw new BusinessException("상품의 재고가 부족합니다.");
            }

            product.decreaseStockQuantity(1);
            productRepository.save(product);
            order.setProduct(product);

            usersRepository.findByEmail(customerEmail)
                    .ifPresentOrElse(
                            C -> C.addOrder(order)
                            , () -> {
                                throw new BusinessException(ExMessage.USER_ERROR_NOT_FOUND);
                            });

            orderResDto = order.toEntity();
            orderResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
            orderResDto.setFailUrl(tossPaymentConfig.getFailUrl());

            return orderResDto;
       } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
    }
    @Transactional
    public void verifyRequest(String paymentKey, String orderId, int amount) {
        // 요청한 결제 금액과 실제 토스페이먼츠에서 결제된 금액 일치 검증
        // 조회는 orderId 기준
        // 추후 결제 조회/취소를 위한 paymentKey 설정
        orderRepository.findByOrderId(orderId)
                .ifPresentOrElse(
                        P -> {
                            if (P.getAmount() == amount) {
                                P.setPaymentKey(paymentKey);
                            } else {
                                throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PRICE);
                            }
                        }, () -> {
                            throw new BusinessException(ExMessage.NOT_YET_DEFINED_ERROR);
                        }
                );
    }

    @Transactional
    public PaymentResSuccessDto requestFinalPayment(String paymentKey, String orderId, int amount) {
        // 토스에서 제공한 시크릿 키
        String testSecretKey = tossPaymentConfig.getTestSecretKey() + ":"; // 시크릿 키는 ":" 을 붙여야 함
        // 토스 : 시크릿 키 뒤에 콜론을 추가해서 비밀번호가 없다는 것을 알립니다.

        Order order = orderRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND));

        // 토스에서 제공한 시크릿 키를 Basic Authorization 방식으로 인코딩해서 전송
        byte[] encodedKey = Base64.getEncoder()
                .encode(testSecretKey
                        .getBytes(StandardCharsets.UTF_8));
        String encodedAuth = new String(encodedKey);
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 설정
        httpHeaders.setBasicAuth(encodedAuth);  // Base64로 인코딩한 문자열을 HTTP 요청 헤더에 추가하여 인증
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); // 콘텐츠 유형을 JSON 형식으로 명시
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));   // 서버로부터 JSON 형식의 데이터를 받는 것을 명시

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderId);
        jsonObject.put("amount", amount);

        PaymentResSuccessDto paymentResSuccessDto;

        String requestUrl = tossPaymentConfig.getBasicUrl() + paymentKey;
        try {
            // 해당 URL에 POST 요청을 보내고 응답을 엔티티 객체로 받음
            paymentResSuccessDto = restTemplate.postForEntity(
                    requestUrl,   // 요청 URL 은 basic_url + paymentKey
                    new HttpEntity<>(jsonObject, httpHeaders),      // 요청 데이터에 추가하는 데이터
                    PaymentResSuccessDto.class                      // 문자열 형태로 응답 받기
            ).getBody();
            if (paymentResSuccessDto == null)
                throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER);
        } catch (Exception e) {
            String errorMessage = parseErrorMessage(e.getMessage());
            throw new BusinessException(errorMessage);
        }

        PaymentResCardDto card = paymentResSuccessDto.getCard();
        orderRepository.findByOrderId(paymentResSuccessDto.getOrderId())
            .ifPresent(P -> {
                        P.setCardNumber(card.getNumber());
                        order.setPaymentType(PAYMENT_TYPE.NORMAL);
                        P.setPaySuccess(true);
            });
        return paymentResSuccessDto;
    }

    private String parseErrorMessage(String errorMessage) {
        // 예외 메시지에서 JSON 부분 추출
        int startIndex = errorMessage.indexOf("{");
        int endIndex = errorMessage.lastIndexOf("}");
        String jsonError = errorMessage.substring(startIndex, endIndex + 1);

        // JSON을 TossErrorDto로 변환
        TossErrorDto tossErrorDto = new Gson().fromJson(jsonError, TossErrorDto.class);

        return tossErrorDto.getMessage();
    }

    @Transactional
    public PaymentFailDto requestFail(String errorCode, String errorMessage, String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND));
        order.setPaySuccess(false);
        order.setPayFailReason(errorMessage);

        return PaymentFailDto
                .builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .orderId(orderId)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments(String customerEmail, PageRequest pageRequest) {
        String targetEmail = usersRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND))
            .getEmail();

        return orderRepository.findAllByUsersEmail(targetEmail, pageRequest)
            .stream().map(Order::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentDto getOnePayment(String customerEmail, String orderId) {
        String targetEmail = usersRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND))
            .getEmail();

        return orderRepository.findByUsersEmailAndOrderId(targetEmail, orderId)
            .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND))
            .toDto();
    }
}
