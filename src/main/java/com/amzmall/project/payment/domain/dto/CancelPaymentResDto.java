package com.amzmall.project.payment.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentResDto {
	private Long cancelPaymentId;
	private String orderId;
	private String paymentKey;
	private String orderName;
	private String requestedAt;
	private String approvedAt;
	private String cardCompany;
	private String cardNumber;
	private String receiptUrl;
	private String cancelReason;
	private Long cancelAmount;
}
