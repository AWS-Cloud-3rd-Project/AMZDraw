package com.amzmall.project.order.adapter.out;

import com.amzmall.project.order.application.port.out.ReadOrderPort;
import com.amzmall.project.order.application.port.out.WriteOrderPort;
import com.amzmall.project.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderPersistenceAdapter implements WriteOrderPort, ReadOrderPort {

    private final SpringDataOrderRepository orderRepository;
    @Override
    public OrderJpaEntity createOrder(Order order) {
        return orderRepository.save(
                new OrderJpaEntity(
                        order.getOrderId(),
                        order.getOrderStatus(),
                        order.getOrderDate(),
                        order.getOrderer(),
                        order.getOrderQuantity(),
                        order.getOrderAmount()
                )
        );
    }

    @Override
    public OrderJpaEntity updateOrder(OrderJpaEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderJpaEntity findByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderJpaEntity> findAllByOrderer(String orderer) {
        return orderRepository.findAllByOrderer(orderer);
    }
}
