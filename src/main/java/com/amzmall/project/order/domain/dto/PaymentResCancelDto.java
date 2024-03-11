package com.amzmall.project.order.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResCancelDto {
    int cancelAmount;       // 결제를 취소한 금액
    String cancelReason;    // 결제를 취소하는 이유
    String canceledAt;      // 결제 취소가 일어난 날짜와 시간, yyyy-MM-dd'T'HH:mm:ss±hh:mm
}
