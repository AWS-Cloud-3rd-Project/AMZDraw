package com.amzmall.project.order.application.port.in;

import com.amzmall.project.order.domain.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReadOrderUseCase {
    OrderDTO findByOrderId(FindOrderCommand command);

    List<OrderDTO> findAllByOrderer(FindOrderCommand command);
}
