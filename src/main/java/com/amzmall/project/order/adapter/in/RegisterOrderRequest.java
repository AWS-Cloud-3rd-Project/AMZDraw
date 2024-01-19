package com.amzmall.project.order.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderRequest {
    private String orderId;
    private String orderStatus;
    private String orderDate;
    private String orderer; // TODO 회원 정보의 이름을 받아
    private String orderAmount;

}
