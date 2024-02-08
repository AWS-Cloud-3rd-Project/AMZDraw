package com.amzmall.project.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.domain.dto.PaymentReqDto;
import com.amzmall.project.domain.dto.PaymentResDto;
import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.config.TossPaymentConfig;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CustomerService customerService;
    private final PaymentRepository paymentRepository;
    private final TossPaymentConfig tosspaymentConfig;
    @Transactional
    public PaymentResDto requestPayments(PaymentReqDto paymentReqDto){
        Long amount = paymentReqDto.getAmount();
        String payType = paymentReqDto.getPayType().name();
        String customerEmail = paymentReqDto.getCustomerEmail();
        String customerName = paymentReqDto.getCustomerName();
        String orderName = paymentReqDto.getOrderName();
        PaymentResDto paymentResDto;

        if (amount == null || amount < 1000L){
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PRICE);       // 주문 금액이 null이거나 1000원 미만이면 BusinessException 발생
        }
        if (!payType.equals("CARD") && !payType.equals("카드")){
            throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_PAY_TYPE);    // 결제 유형이 "CARD" 또는 "카드"가 아니면 BusinessException이 발생
        }

        try {
            Payment payment = paymentReqDto.toEntity();
            paymentResDto = payment.toPaymentDto();
            paymentResDto.setSuccessUrl(tosspaymentConfig.getSuccessUrl());
            paymentResDto.setFailUrl(tosspaymentConfig.getSuccessUrl());
            paymentResDto.setCustomerEmail(customerEmail);
            paymentResDto.setCustomerName(customerName);
            paymentResDto.setPaymentKey("123456");
            paymentRepository.save(payment);
        }catch(Exception e){
            throw new BusinessException(ExMessage.DB_ERROR_SAVE);
        }
        return paymentResDto;
    }
}
