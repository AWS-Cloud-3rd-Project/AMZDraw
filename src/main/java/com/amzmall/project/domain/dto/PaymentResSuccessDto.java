package com.amzmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResSuccessDto {
    String mId;                     // 가맹점 ID
    String paymentKey;
    String orderId;
    String orderName;
    String method;                  // 결제수단
    String totalAmount;
    String status;
    PaymentResCardDto card;
    PaymentResCancelDto[] cancels;
}
