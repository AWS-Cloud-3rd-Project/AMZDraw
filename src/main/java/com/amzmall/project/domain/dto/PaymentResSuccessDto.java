package com.amzmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResSuccessDto {
    String mId;                     // 상점아이디(MID). 토스페이먼츠에서 발급
    String version;                 // Payment 객체의 응답 버전. 버전 2022-06-08부터 날짜 기반 버저닝
    String paymentKey;              // 결제의 키 값. 중복되지 않는 고유한 값. 결제 승인, 결제 조회, 결제 취소 API에 사용
    String orderId;                 // 주문 ID. 가맹점에서 만들어서 사용한 값. 결제 데이터 관리를 위해 반드시 저장
    String orderName;               // 주문명
    String currency;                // 결제할 때 사용한 통화
    String method;                  // 결제수단 (카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권)
    String totalAmount;             // 총 결제 금액
    String balanceAmount;           // 취소할 수 있는 금액(잔고)
    String suppliedAmount;          // 결제 처리 상태
    String vat;                     // 부가세
    String status;                  // 결제 처리 상태  : "DONE"
    String requestedAt;             // 결제가 일어난 날짜와 시간 정보. yyyy-MM-dd'T'HH:mm:ss±hh:mm
    String approvedAt;              // 결제 승인이 일어난 날짜와 시간 정보입니다. yyyy-MM-dd'T'HH:mm:ss±hh:mm
    String useEscrow;               // 에스크로 사용 여부
    String cultureExpense;          // 문화비 지출 여부, 카드 결제는 항상 false
    String type;                    // 결제 타입 정보 (NOMAL, BILLING, CONNECTPAY)
    PaymentResCardDto card;	        // 카드 결제
}
