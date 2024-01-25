package com.amzmall.project.order.adapter.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.order.domain.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
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
    private LocalDate orderDate;
    //주문자
    private String orderer;
    //주문 수량
    private int orderQuantity;
    //주문 금액
    private int orderAmount;

    //연관관계 매핑
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private DeliveryJpaEntity deliveryJpaEntity;

    public OrderJpaEntity(String orderId, Order.OrderStatus orderStatus, LocalDate orderDate, String orderer, int orderQuantity, int orderAmount) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }
}
