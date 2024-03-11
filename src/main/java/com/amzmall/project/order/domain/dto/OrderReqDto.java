package com.amzmall.project.order.domain.dto;

import com.amzmall.project.order.domain.entity.Order;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "주문 요청 DTO")
public class OrderReqDto {
    @Schema(description = "주문 상품")
    private int productId;
    @Schema(description = "결제 금액")
    private int amount;
    @Schema(description = "주문 이름")
    private String orderName;
    @Schema(description = "구매자 이메일")
    private String customerEmail;
    @Schema(description = "구매자 이름")
    private String customerName;
    @Schema(description = "구매자 주소")
    private String customerAddress;
    @Schema(description = "구매자 핸드폰 번호")
    private String customerPhone;

    public Order toEntity() {
        return Order.builder()
            .orderId(UUID.randomUUID().toString())
            .amount(amount)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .customerAddress(customerAddress)
            .customerPhone(customerPhone)
            .isPaySuccess(false)
            .isPayCancled(false)
            .build();
    }
}