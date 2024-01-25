package com.amzmall.project.order.application.port.in;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderCommand {

    private String orderId;
    private String orderer;
    private int orderQuantity;
    private int orderAmount;

}
