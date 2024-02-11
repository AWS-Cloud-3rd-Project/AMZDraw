package com.amzmall.project.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.domain.dto.PaymentReqDto;
import com.amzmall.project.domain.dto.PaymentResCardDto;
import com.amzmall.project.domain.dto.PaymentResDto;
import com.amzmall.project.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.config.TossPaymentConfig;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.repository.CustomerRepository;
import com.amzmall.project.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CustomerService customerService;
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final TossPaymentConfig tossPaymentConfig;

    @Value("${toss.payments.basic_url}")
    private String basicUrl;

    @Transactional
    public PaymentResDto requestPayments(PaymentReqDto paymentReqDto){
        Long amount = paymentReqDto.getAmount();
        String payType = paymentReqDto.getPaymentType().getName();
        String customerEmail = paymentReqDto.getCustomerEmail();

        if (amount == null || amount <= 1000) {
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PRICE);
        }

        if (!payType.equals("일반결제") && !payType.equals("브랜드페이")) {
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PAY_TYPE);
        }

        PaymentResDto paymentResDto;
        try {
            Payment payment = paymentReqDto.toEntity();
            customerRepository.findByEmail(customerEmail)
                    .ifPresentOrElse(
                            M -> M.addPayment(payment)
                            , () -> {
                                throw new BusinessException(ExMessage.CUSTOMER_ERROR_NOT_FOUND);
                            });
            paymentResDto = payment.toPaymentDto();
            paymentResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
            paymentResDto.setFailUrl(tossPaymentConfig.getFailUrl());
            return paymentResDto;
        } catch (Exception e) {
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
    }
    @Transactional
    public void verifyRequest(String paymentKey, String orderId, Long amount) {
        // 요청한 결제 금액과 실제 토스페이먼츠에서 결제된 금액 일치 검증
        // 조회는 orderId 기준
        // 추후 결제 조회/취소를 위한 paymentKey 설정
        paymentRepository.findByOrderId(orderId)
                .ifPresentOrElse(
                        P -> {
                            if (P.getAmount().equals(amount)) {
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
    public PaymentResSuccessDto requestFinalPayment(String paymentKey, String orderId, Long amount) {
        // 토스에서 제공한 시크릿 키
        String testSecretKey = tossPaymentConfig.getTestSecretKey(); // 시크릿 키는 ":" 을 붙여야 함
        // 토스 : 시크릿 키 뒤에 콜론을 추가해서 비밀번호가 없다는 것을 알립니다.

        Payment pay = paymentRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOTFOUND));

        PAYMENT_TYPE payType = pay.getPaymentType();

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


        // 해당 URL에 POST 요청을 보내고 응답을 엔티티 객체로 받음
        paymentResSuccessDto = restTemplate.postForEntity(
                basicUrl + paymentKey,   // 요청 URL 은 basic_url + paymentKey
                    new HttpEntity<>(jsonObject, httpHeaders),      // 요청 데이터에 추가하는 데이터
                    PaymentResSuccessDto.class                                    // 문자열 형태로 응답 받기
            ).getBody();
        System.out.println(basicUrl + paymentKey);
        if (payType.equals(PAYMENT_TYPE.NORMAL)) {
            PaymentResCardDto card = paymentResSuccessDto.getCard();
            paymentRepository.findByOrderId(paymentResSuccessDto.getOrderId())
                    .ifPresent(payment -> {
//                        payment.setCardCompany(card.getCompany());
//                        payment.setCardNumber(card.getNumber());
                        payment.setPaySuccessYn(true);
                    });
        }
        return paymentResSuccessDto;
    }
}