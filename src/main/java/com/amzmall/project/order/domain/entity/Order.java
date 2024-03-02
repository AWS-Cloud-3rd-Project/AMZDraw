package com.amzmall.project.order.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //주문 번호
    private String orderId;
    //주문 상태
    private String orderStatus; // TODO enum으로 변경 (주문 대기, 주문 완료, 주문 취소, 주문 삭제 대기, 주문 삭제)
    //주문 날짜
    private String orderDate;
    //주문자
    private String orderer;
    //주문 금액
    private String orderAmount; // TODO orderProduct의 금액의 총합
    //주문 상품
    private String orderProduct;
    public Order(String orderId, String orderStatus, String orderDate, String orderer, String orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderAmount = orderAmount;
        this.orderProduct = orderProduct;
    }
}
