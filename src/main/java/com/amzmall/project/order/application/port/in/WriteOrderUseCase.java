package com.amzmall.project.order.application.port.in;

import com.amzmall.project.order.domain.OrderDTO;
import org.springframework.stereotype.Component;

public interface WriteOrderUseCase {
    OrderDTO registerOrder(RegisterOrderCommand command);
}
