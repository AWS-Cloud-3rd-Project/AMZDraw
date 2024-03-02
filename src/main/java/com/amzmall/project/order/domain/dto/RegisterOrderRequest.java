package com.amzmall.project.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderRequest {
    private String orderId; // TODO 주문번호 생성 uuid
    private String orderStatus; // 주문상태
    private String orderDate; // 주문시간
    private String orderer; // 주문자
    private String orderAmount;//주문금액
    private String orderProduct;

}
