package com.amzmall.project.order.adapter.out;

import com.amzmall.project.order.application.port.out.FindOrderPort;
import com.amzmall.project.order.application.port.out.RegisterOrderPort;
import com.amzmall.project.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RegisterOrderPersistenceAdapter implements RegisterOrderPort, FindOrderPort {

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

    @Override
    public OrderJpaEntity findByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderJpaEntity> findAllByOrderer(String orderer) {
        return orderRepository.findAllByOrderer(orderer);
    }
}
