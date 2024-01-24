package com.amzmall.project.order.application.port.in;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderCommand {

    private String orderId;
    private String orderDate;
    private String orderer;
    private String orderQuantity;
    private String orderAmount;

}
