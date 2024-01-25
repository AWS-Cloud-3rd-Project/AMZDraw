package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.domain.OrderDTO;
import com.amzmall.project.order.domain.OrderStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderWriteController {

    private final WriteOrderUseCase writeOrderUseCase;


    @PostMapping("/register")
    public OrderDTO registerOrder(@RequestBody RegisterOrderRequest orderRequest) {
        RegisterOrderCommand command = RegisterOrderCommand.builder()
                .orderId(orderRequest.getOrderId())
                .orderer(orderRequest.getOrderer())
                .orderQuantity(orderRequest.getOrderQuantity())
                .orderAmount(orderRequest.getOrderAmount())
                .build();
        return writeOrderUseCase.registerOrder(command);
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<OrderStatusDTO> completeOrder(@PathVariable(name = "orderId") String orderId) {
        OrderStatusDTO orderStatusDTO = writeOrderUseCase.completeOrder(orderId);
        return ResponseEntity.ok(orderStatusDTO);
    }
}
