package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.application.port.out.RegisterOrderPort;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteOrderService implements WriteOrderUseCase {

    private final RegisterOrderPort registerOrderPort;
    private final ReadOrderService readService;

    @Override
    public OrderDTO registerOrder(RegisterOrderCommand command) {
        OrderJpaEntity orderEntity = registerOrderPort.createOrder(
                Order.builder()
                        .orderId(command.getOrderId())
                        .orderStatus(command.getOrderStatus())
                        .orderDate(command.getOrderDate())
                        .orderer(command.getOrderer())
                        .orderAmount(command.getOrderAmount())
                        .build()
        );
    return readService.toOrderDto(orderEntity);
    }
}
