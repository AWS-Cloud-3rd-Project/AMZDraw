package com.amzmall.project.order.adapter.in;

import com.amzmall.project.order.application.port.in.FindOrderCommand;
import com.amzmall.project.order.application.port.in.ReadOrderUseCase;
import com.amzmall.project.order.domain.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class FindOrderController {

    private final ReadOrderUseCase readOrderUseCase;

    //주문 정보 조회
    @GetMapping(path = "/{orderId}")
    public OrderDTO findOrderByOrderId(@PathVariable(name = "orderId") String orderId) {

        FindOrderCommand command = FindOrderCommand.builder()
                .orderId(orderId)
                .build();

        return readOrderUseCase.findOrderById(command);
    }
}
