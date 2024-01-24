package com.amzmall.project.order.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderDTO {

    //주문 번호
    private String orderId;
    //주문 상태
    private Order.OrderStatus orderStatus; // (주문 대기=장바구니, 주문 완료=결제 후, 주문 취소)
    //주문 날짜
    private String orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private String orderQuantity;
    //주문 금액
    private String orderAmount;

    @Builder
    public OrderDTO(String orderId, Order.OrderStatus orderStatus, String orderDate, String orderer, String orderQuantity, String orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }
}