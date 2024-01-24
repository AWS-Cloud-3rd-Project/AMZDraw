package com.amzmall.project.order.application.port.out;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.Order;
import org.springframework.stereotype.Component;

public interface WriteOrderPort {

    OrderJpaEntity createOrder(Order order);
}
