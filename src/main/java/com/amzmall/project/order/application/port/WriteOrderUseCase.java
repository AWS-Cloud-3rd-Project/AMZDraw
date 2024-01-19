package com.amzmall.project.order.application.port;

import com.amzmall.project.order.domain.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public interface WriteOrderUseCase {
    OrderDTO registerOrder(RegisterOrderCommand command);
}
