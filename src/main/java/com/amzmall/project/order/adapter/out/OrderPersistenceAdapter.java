package com.amzmall.project.order.adapter.out;

import com.amzmall.project.order.application.port.OrderPort;
import com.amzmall.project.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderPersistenceAdapter implements OrderPort {

    private final SpringDataOrderRepository orderRepository;
    @Override
    public OrderJpaEntity createOrder(Order order) {
        return orderRepository.save(
                new OrderJpaEntity(
                        order.getOrderId(),
                        order.getOrderStatus(),
                        order.getOrderDate(),
                        order.getOrderer(),
                        order.getOrderAmount()
                )
        );
    }
}
