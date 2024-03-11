package com.amzmall.project.order.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String paymentType;        // 결제 유형
    private int amount;                // 결제 금액
    private String cardNumber;         // 카드 번호
    private String orderId;            // 주문 ID
    private String orderName;          // 주문 상품명
    private String customerEmail;      // 고객 이메일
    private String customerName;       // 고객 이름
    private String customerAddress;    // 주소
    private String paymentKey;         // 결제 키
    private boolean isPaySuccess;      // 결제 성공 여부
    private String payFailReason;      // 결제 실패 이유
    private boolean isPayCancled;    // 결제 취소 여부
    private String createdAt;        // 생성일
}

