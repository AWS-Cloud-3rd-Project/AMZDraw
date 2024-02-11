package com.amzmall.project.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String paymentType;
    private Long amount;
    private String cardCompany;
    private String cardNumber;
    private String orderId;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private String paymentKey;
    private String paySuccessYn;
    private String payFailReason;
    private String cancelYn;
    private String createDate;
}
