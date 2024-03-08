package com.amzmall.project.payment.service;

import com.amzmall.project.util.advice.ExMessage;
import com.amzmall.project.customer.service.CustomerService;
import com.amzmall.project.payment.domain.dto.PaymentDto;
import com.amzmall.project.payment.domain.dto.PaymentFailDto;
import com.amzmall.project.payment.domain.dto.PaymentReqDto;
import com.amzmall.project.payment.domain.dto.PaymentResCardDto;
import com.amzmall.project.payment.domain.dto.PaymentResDto;
import com.amzmall.project.payment.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.payment.domain.dto.TossErrorDto;
import com.amzmall.project.payment.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.payment.domain.entity.Payment;
import com.amzmall.project.payment.config.TossPaymentConfig;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.customer.repository.CustomerRepository;
import com.amzmall.project.payment.repository.PaymentRepository;
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
    private final CustomerService customerService;
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final TossPaymentConfig tossPaymentConfig;

    @Transactional
    public PaymentResDto requestPayment(PaymentReqDto paymentReqDto){
        int amount = paymentReqDto.getAmount();
        String paymentType = paymentReqDto.getPaymentType().getName();
        String customerEmail = paymentReqDto.getCustomerEmail();
        String customerName = paymentReqDto.getCustomerName();
        String orderName = paymentReqDto.getOrderName();
        String orderID = paymentReqDto.getOrderId();

        if (amount <= 1000) {
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PRICE);
        }

        if (!paymentType.equals("일반결제") && !paymentType.equals("브랜드페이")) {
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PAYMENT_TYPE);
        }

        if (paymentRepository.findByOrderId(orderID).isPresent()) {
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ALREADY_APPLY);
        }

        PaymentResDto paymentResDto;
        try {
            Payment payment = paymentReqDto.toEntity();
            customerRepository.findByEmail(customerEmail)
                    .ifPresentOrElse(
                            C -> C.addPayment(payment)
                            , () -> {
                                throw new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND);
                            });
            paymentRepository.save(payment);
            paymentResDto = payment.toPaymentDto();
            paymentResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
            paymentResDto.setFailUrl(tossPaymentConfig.getFailUrl());

            return paymentResDto;
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
    }
    @Transactional
    public void verifyRequest(String paymentKey, String orderId, int amount) {
        // 요청한 결제 금액과 실제 토스페이먼츠에서 결제된 금액 일치 검증
        // 조회는 orderId 기준
        // 추후 결제 조회/취소를 위한 paymentKey 설정
        paymentRepository.findByOrderId(orderId)
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

        Payment payment = paymentRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND));

        PAYMENT_TYPE payType = payment.getPaymentType();

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

        // 일반 카드 결제 시
        if (payType.equals(PAYMENT_TYPE.NORMAL)) {
            PaymentResCardDto card = paymentResSuccessDto.getCard();
            paymentRepository.findByOrderId(paymentResSuccessDto.getOrderId())
                    .ifPresent(P -> {
                        P.setCardNumber(card.getNumber());
                        P.setPaySuccess(true);
                        // Order 엔티티 추가 시 설정
                        // Order.setPayYn("Y");
                        //Order.setPayType(PAY_TYPE.NORMAL);
                    });
        }
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
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND));
        payment.setPaySuccess(false);
        payment.setPayFailReason(errorMessage);

        return PaymentFailDto
                .builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .orderId(orderId)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments(String customerEmail, PageRequest pageRequest) {
        String targetEmail = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND))
            .getEmail();

        return paymentRepository.findAllByCustomerEmail(targetEmail, pageRequest)
            .stream().map(Payment::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentDto getOnePayment(String customerEmail, String orderId) {
        String targetEmail = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND))
            .getEmail();

        return paymentRepository.findByCustomerEmailAndOrderId(targetEmail, orderId)
            .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND))
            .toDto();
    }
}
