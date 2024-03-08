package com.amzmall.project.order.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResDto {
    // PaymentReqDto로 받은 정보 검증 후 토스페이먼츠에 실제 결제 요청을 하기 위해 필요한 값을 포함하여 PaymentResDto로 반환
    private String paymentType;			// 지불 방법
    private int amount;			// 지불 금액
    private String orderName;		// 주문 상품 이름
    private String orderId;			// 주문 고유 ID
    private String customerEmail;	// 구매자 이메일
    private String customerName;

    private String paymentKey;		// 결제 고유 키
    private String successUrl;		// 성공 리다이렉트 URL
    private String failUrl;			// 실패 리다이렉트 URL

    private String createdDate;
}