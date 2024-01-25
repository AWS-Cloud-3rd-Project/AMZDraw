package com.amzmall.project.order.application.port.in;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.OrderDTO;
import com.amzmall.project.order.domain.OrderStatusDTO;
import org.springframework.stereotype.Component;

public interface WriteOrderUseCase {
    OrderDTO registerOrder(RegisterOrderCommand command);
    OrderStatusDTO completeOrder(String orderId);
}
