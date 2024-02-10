package com.amzmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResSuccessDto {
    String mId;                     // : "tosspayments", 가맹점 ID
    String version;                 // : "1.3", Payment 객체 응답 버전
    String paymentKey;              // : "5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6",
    String orderId;                 // : "IBboL1BJjaYHW6FA4nRjm",
    String orderName;               // : "토스 티셔츠 외 2건",
    String currency;                // : "KRW",
    String method;                  // : "카드", 결제수단
    String totalAmount;             // : 15000,
    String balanceAmount;           // : 15000,
    String suppliedAmount;          // : 13636,
    String vat;                     // : 1364,
    String status;                  // : "DONE", 결제 처리 상태
    String requestedAt;             // : "2021-01-01T10:01:30+09:00",
    String approvedAt;              // : "2021-01-01T10:05:40+09:00",
    String useEscrow;               // : false,
    String cultureExpense;          // : false,
    PaymentResCardDto card;	// : 카드 결제,
    PaymentResCancelDto[] cancels;	// : 결제 취소 이력 관련 객체
    String type;                    // : "NORMAL",	결제 타입 정보 (NOMAL, BILLING, CONNECTPAY)
}
