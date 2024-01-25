package com.amzmall.project.order.application.port.out;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderStatusDTO;
import org.springframework.stereotype.Component;

public interface WriteOrderPort {

    OrderJpaEntity createOrder(Order order);

    OrderJpaEntity updateOrder(OrderJpaEntity orderEntity);

}
