package com.amzmall.project.order.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

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
    @CreatedDate
    private LocalDate orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private int orderQuantity;
    //주문 금액
    private int orderAmount;

    @Builder
    public Order(String orderId, OrderStatus orderStatus, LocalDate orderDate, String orderer, int orderQuantity, int orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }

    //주문 상태 변경
    public Order completeOrder() {
        if (this.orderStatus == OrderStatus.ORDER_WAITING) {
            this.orderStatus = OrderStatus.ORDER_COMPLETED;
            return this;
        } else {
            throw new IllegalStateException("주문이 '대기중' 상태에서만 완료될 수 있습니다.");
        }
    }
}