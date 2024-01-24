package com.amzmall.project.order.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
    //enum 주문 상태
    public enum OrderStatus {
        ORDER_WAITING, ORDER_COMPLETED, ORDER_CANCEL
    }
    //주문 번호
    private String orderId;
    //주문 상태
    private OrderStatus orderStatus; // (주문 대기=장바구니, 주문 완료=결제 후, 주문 취소)
    //주문 날짜
    private String orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private String orderQuantity;
    //주문 금액
    private String orderAmount;

    @Builder
    public Order(String orderId, OrderStatus orderStatus, String orderDate, String orderer, String orderQuantity, String orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }
}