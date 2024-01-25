package com.amzmall.project.order.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {

    //주문 번호
    private String orderId;
    //주문 상태
    private Order.OrderStatus orderStatus; // (주문 대기=장바구니, 주문 완료=결제 후, 주문 취소)
    //주문 날짜
    private LocalDate orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private int orderQuantity;
    //주문 금액
    private int orderAmount;

    @Builder
    public OrderDTO(String orderId, Order.OrderStatus orderStatus, LocalDate orderDate, String orderer, int orderQuantity, int orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }
}