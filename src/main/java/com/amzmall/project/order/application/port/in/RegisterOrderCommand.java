package com.amzmall.project.order.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@NonNull
public class RegisterOrderCommand {

    private final String orderId;
    private final String orderStatus;
    private final String orderDate;
    private final String orderer;
    private final String orderAmount;

    public RegisterOrderCommand(String orderId, String orderStatus, String orderDate, String orderer, String orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderAmount = orderAmount;
    }

}
