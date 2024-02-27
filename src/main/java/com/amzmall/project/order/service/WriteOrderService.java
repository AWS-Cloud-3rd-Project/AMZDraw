package com.amzmall.project.order.service;


import com.amzmall.project.order.domain.dto.RegisterOrderRequest;
import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteOrderService {

    private final OrderRepository orderRepository;

    public void registerOrder(RegisterOrderRequest orderRequest) {
        Order order = Order.builder()
                .orderId(orderRequest.getOrderId())
                .orderStatus(orderRequest.getOrderStatus())
                .orderDate(orderRequest.getOrderDate())
                .orderer(orderRequest.getOrderer())
                .orderAmount(orderRequest.getOrderAmount())
                .build();
        orderRepository.save(order);
    }

}
