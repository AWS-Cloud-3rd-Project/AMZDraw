package com.amzmall.project.admin.domain.order;

import com.amzmall.project.admin.enums.OrderStatus;
import com.amzmall.project.admin.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class OrderDetailView {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private PayType payType;
    private OffsetDateTime createdAt;
}