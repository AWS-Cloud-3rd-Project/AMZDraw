package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.WriteOrderUseCase;
import com.amzmall.project.order.application.port.RegisterOrderCommand;
import com.amzmall.project.order.application.port.OrderPort;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteOrderService implements WriteOrderUseCase {

    private final OrderPort orderPort;
    private final ReadOrderService readService;

    @Override
    public OrderDTO registerOrder(RegisterOrderCommand command) {
        OrderJpaEntity orderEntity = orderPort.createOrder(
                Order.builder()
                        .orderId(command.getOrderId())
                        .orderStatus(command.getOrderStatus())
                        .orderDate(command.getOrderDate())
                        .orderer(command.getOrderer())
                        .orderAmount(command.getOrderAmount())
                        .build()
        );
    return readService.toDto(orderEntity);
    }
}
