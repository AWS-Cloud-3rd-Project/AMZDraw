package com.amzmall.project.domain.dto;
import com.amzmall.project.domain.entity.PayType;
import com.amzmall.project.domain.entity.Payment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReqDto {
	private PayType payType;
	private Long amount;
	private String orderName;

	private String yourSuccessUrl;
	private String yourFailUrl;

	public Payment toEntity() {
		return Payment.builder()
			.payType(payType)
			.amount(amount)
			.orderName(orderName)
			.orderId(UUID.randomUUID().toString())
			.paySuccessYN(false)
			.build();
	}
}
