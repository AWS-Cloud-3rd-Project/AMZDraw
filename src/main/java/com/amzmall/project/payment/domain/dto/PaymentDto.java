package com.amzmall.project.payment.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String paymentType;
    private int amount;
    private String cardCompany;
    private String cardNumber;
    private String orderId;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private String paymentKey;
    private boolean isPaySuccess;
    private String payFailReason;
    private boolean isPayCancled;
    private String createDate;
}
