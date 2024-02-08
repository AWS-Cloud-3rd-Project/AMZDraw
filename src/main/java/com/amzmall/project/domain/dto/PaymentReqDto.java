package com.amzmall.project.domain.dto;

import com.amzmall.project.domain.entity.PayType;
import com.amzmall.project.domain.entity.Payment;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReqDto {
	private PayType payType;
	private Long amount;
	private String orderId;
	private String orderName;
	private String customerEmail;
	private String customerName;

	// 프론트에서 입력받는 값
	public Payment toEntity() {
		return Payment.builder()
				.orderId(UUID.randomUUID().toString())
				.payType(payType)
				.amount(amount)
				.orderName(orderName)
				.customerEmail(customerEmail)
				.customerName(customerName)
				.paySuccessYn(true)
				.build();
	}
}
