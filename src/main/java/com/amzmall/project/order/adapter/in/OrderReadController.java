package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.domain.OrderDTO;
import com.amzmall.project.order.domain.OrderStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderReadController {

    private final ReadOrderUseCase readOrderUseCase;

    //주문 정보 조회
    @GetMapping(path = "/{orderId}")
    public OrderDTO findOrderByOrderId(@PathVariable(name = "orderId") String orderId) {

        FindOrderCommand orderCommand = FindOrderCommand.builder()
                .orderId(orderId)
                .build();

        return readOrderUseCase.findByOrderId(orderCommand);
    }

    //주문 목록 조회
    @GetMapping(path = "/{orderer}/orderList")
    public List<OrderDTO> findOrdersByOrderer(@PathVariable(name = "orderer") String orderer) {

        FindOrderCommand ordersCommand = FindOrderCommand.builder()
                .orderer(orderer)
                .build();

        return readOrderUseCase.findAllByOrderer(ordersCommand);
    }
}
