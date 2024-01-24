package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.application.port.out.WriteOrderPort;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteOrderService implements WriteOrderUseCase {

    private final WriteOrderPort writeOrderPort;
    private final ReadOrderService readService;

    @Override
    public OrderDTO registerOrder(RegisterOrderCommand command) {

        Order order = Order.builder()
                .orderId(command.getOrderId())
                .orderStatus(Order.OrderStatus.ORDER_COMPLETED)
                .orderDate(command.getOrderDate())
                .orderer(command.getOrderer())
                .orderQuantity(command.getOrderQuantity())
                .orderAmount(command.getOrderAmount())
                .build();

        OrderJpaEntity orderJpaEntity = writeOrderPort.createOrder(order);
        return readService.toOrderDto(orderJpaEntity);
    }
}
