package com.amzmall.project.order.application.port.out;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public interface RegisterOrderPort {

    OrderJpaEntity createOrder(Order order);
}
