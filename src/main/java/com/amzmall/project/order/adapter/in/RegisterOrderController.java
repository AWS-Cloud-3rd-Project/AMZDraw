package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class RegisterOrderController {

    private final WriteOrderUseCase writeOrderUseCase;

    @PostMapping("")
    public OrderDTO registerOrder(@RequestBody RegisterOrderRequest orderRequest) {
        RegisterOrderCommand command = RegisterOrderCommand.builder()
                .orderId(orderRequest.getOrderId())
                .orderStatus(orderRequest.getOrderStatus())
                .orderDate(orderRequest.getOrderDate())
                .orderer(orderRequest.getOrderer())
                .orderAmount(orderRequest.getOrderAmount())
                .build();
        return writeOrderUseCase.registerOrder(command);
    }
}
