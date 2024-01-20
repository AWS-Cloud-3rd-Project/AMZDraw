package com.amzmall.project.order.application.port.in;

import com.amzmall.project.order.domain.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public interface ReadOrderUseCase {

    OrderDTO findOrderById(FindOrderCommand command);
}
