package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.OrderPort;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderService {

    private final OrderPort orderPort;

    public OrderDTO toDto(OrderJpaEntity orderEntity) {
        return new OrderDTO(
                orderEntity.getOrderId(),
                orderEntity.getOrderStatus(),
                orderEntity.getOrderDate(),
                orderEntity.getOrderer(),
                orderEntity.getOrderAmount()
        );
    }
}
