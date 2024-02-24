package com.amzmall.project.admin.service.dto;

import com.amzmall.project.admin.enums.OrderStatus;
import com.amzmall.project.admin.enums.PayType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data(staticConstructor = "of")
public class OrderDTO {
    private final Long orderId;
    private final BigDecimal amount;
    private final Long customerId;
    private final String customerName;
    private final PayType payType;
    private final OrderStatus orderStatus;
    private final OffsetDateTime orderDate;
}