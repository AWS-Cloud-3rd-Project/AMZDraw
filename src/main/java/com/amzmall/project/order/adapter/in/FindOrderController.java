package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class FindOrderController {

    private final ReadOrderUseCase readOrderUseCase;

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
