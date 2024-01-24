package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final WriteOrderUseCase writeOrderUseCase;
    private final ReadOrderUseCase readOrderUseCase;

    @PostMapping("/register")
    public OrderDTO registerOrder(@RequestBody RegisterOrderRequest orderRequest) {
        RegisterOrderCommand command = RegisterOrderCommand.builder()
                .orderId(orderRequest.getOrderId())
                .orderDate(orderRequest.getOrderDate())
                .orderer(orderRequest.getOrderer())
                .orderQuantity(orderRequest.getOrderQuantity())
                .orderAmount(orderRequest.getOrderAmount())
                .build();
        return writeOrderUseCase.registerOrder(command);
    }


    //주문 정보 조회
    @GetMapping(path = "/{orderId}")
    public OrderDTO findOrderByOrderId(@PathVariable(name = "orderId") String orderId) {

        FindOrderCommand orderCommand = FindOrderCommand.builder()
                .orderId(orderId)
                .build();

        return readOrderUseCase.findOrderByOrderId(orderCommand);
    }

    //주문 목록 조회 TODO: 주문자 아이디로 조회
    @GetMapping(path = "/{orderer}/orderList")
    public List<OrderDTO> findOrdersByOrderer(@PathVariable(name = "orderer") String orderer) {

        FindOrderCommand ordersCommand = FindOrderCommand.builder()
                .orderer(orderer)
                .build();

        return readOrderUseCase.findOrdersByOrderer(ordersCommand);
    }
}
