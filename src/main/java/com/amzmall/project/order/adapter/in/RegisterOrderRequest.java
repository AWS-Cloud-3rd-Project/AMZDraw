package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderRequest {
    private String orderId;
    private String orderDate;
    private String orderer;
    private String orderQuantity;
    private String orderAmount;
}
