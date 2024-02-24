package com.amzmall.project.payment.domain.dto;

import com.amzmall.project.payment.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.payment.domain.entity.Payment;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "구매 요청 DTO")
public class PaymentReqDto {
    @Schema(description = "결제 방식")
    private PAYMENT_TYPE paymentType;
    @Schema(description = "결제 금액")
    private int amount;
    @Schema(description = "주문 ID")
    private String orderId;
    @Schema(description = "주문 이름")
    private String orderName;
    @Schema(description = "구매자 이메일")
    private String customerEmail;
    @Schema(description = "구매자 이름")
    private String customerName;

    // 프론트에서 입력받는 값
    public Payment toEntity() {
        return Payment.builder()
            .orderId(orderId)		// UUID.randomUUID().toString()
            .paymentType(paymentType)
            .amount(amount)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .paySuccessYn("Y")
            .build();
    }
}