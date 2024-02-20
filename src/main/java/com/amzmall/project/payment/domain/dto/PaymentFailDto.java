package com.amzmall.project.payment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFailDto {
    String errorCode;       // 에러 코드
    String errorMessage;    // 에러메세지
    String orderId;         // 주문 고유 ID
}
