package com.amzmall.project.order.controller;

import com.amzmall.project.order.domain.dto.RegisterOrderRequest;
import com.amzmall.project.order.domain.dto.OrderDTO;
import com.amzmall.project.order.service.WriteOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class RegisterOrderController {

    private final WriteOrderService writeOrderService;

    @PostMapping("/register")
    public void registerOrder(@RequestBody RegisterOrderRequest orderRequest) {
        writeOrderService.registerOrder(orderRequest);

    }
}
