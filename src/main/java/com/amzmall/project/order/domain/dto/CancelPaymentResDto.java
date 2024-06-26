package com.amzmall.project.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentResDto {
	private int cancelPaymentId;
	private String orderId;
	private String paymentKey;
	private String orderName;
	private String requestedAt;
	private String approvedAt;
	private String cardNumber;
	private String cancelReason;
	private int cancelAmount;
}
