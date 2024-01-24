package com.amzmall.project.order.adapter.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.order.domain.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //주문 번호
    private String orderId;
    //주문 상태
    private Order.OrderStatus orderStatus;
    //주문 날짜
    private String orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private String orderQuantity;
    //주문 금액
    private String orderAmount;


    public OrderJpaEntity(String orderId, Order.OrderStatus orderStatus, String orderDate, String orderer, String orderQuantity, String orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }
}
