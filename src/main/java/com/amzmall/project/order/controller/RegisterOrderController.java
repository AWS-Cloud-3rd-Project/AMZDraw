package com.amzmall.project.order.controller;

import com.amzmall.project.order.domain.dto.RegisterOrderRequest;
import com.amzmall.project.order.domain.dto.OrderDTO;
import com.amzmall.project.order.service.WriteOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "order_register", description="주문생성")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class RegisterOrderController {

    private final WriteOrderService writeOrderService;

    @PostMapping("/register")
    @Operation(summary="주문 생성", description = "주문을 생성합니다.")
    public void registerOrder(@RequestBody RegisterOrderRequest orderRequest) {
        writeOrderService.registerOrder(orderRequest);

    }
}
