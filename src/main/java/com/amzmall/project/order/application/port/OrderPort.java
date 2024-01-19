package com.amzmall.project.order.application.port;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public interface OrderPort {

    OrderJpaEntity createOrder(Order order);
}
