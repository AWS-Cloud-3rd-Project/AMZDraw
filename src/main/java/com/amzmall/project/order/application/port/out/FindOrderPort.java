package com.amzmall.project.order.application.port.out;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import org.springframework.stereotype.Component;

@Component
public interface FindOrderPort {

    OrderJpaEntity findOrderByOrderId(String orderId);
}
