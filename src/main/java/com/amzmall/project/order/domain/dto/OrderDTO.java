package com.amzmall.project.order.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class OrderDTO {

    //주문 번호
    private String orderId;
    //주문 상태
    private String orderStatus; // TODO enum으로 변경 (주문 대기, 주문 완료, 주문 취소)
    //주문 날짜
    private String orderDate;
    //주문자
    private String orderer;
    //주문 금액
    private String orderAmount;
    //주문 상품
    private String orderProduct;

    public OrderDTO(String orderId, String orderStatus, String orderDate, String orderer, String orderAmount, String orderProduct) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderAmount = orderAmount;
        this.orderProduct = orderProduct;
    }
}