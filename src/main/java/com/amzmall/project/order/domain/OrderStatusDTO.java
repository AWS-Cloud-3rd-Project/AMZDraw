package com.amzmall.project.order.domain;

import lombok.Data;

@Data
public class OrderStatusDTO {

    private String orderId;
    private Order.OrderStatus orderStatus;

    public OrderStatusDTO(String orderId, Order.OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
