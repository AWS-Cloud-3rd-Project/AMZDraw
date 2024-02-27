package com.amzmall.project.order.controller;

import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.order.service.ReadOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class FindOrderController {

    private final ReadOrderService readOrderService;
    // 주문 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = readOrderService.findAllOrders();
        return ResponseEntity.ok(orders);

    }

    //주문id 정보 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderByOrderId(@PathVariable(name = "orderId") String orderId) {
        Order order = readOrderService.findIdOrder(orderId);
        return ResponseEntity.ok(order);
    }

    //주문 목록 조회(내 주문내역 확인에 필요) TODO: 주문자 아이디로 조회
    @GetMapping("/{orderer}/orderList")
    public ResponseEntity<List<Order>> findOrdersByOrderer(@PathVariable(name = "orderer") String orderer) {
        List<Order> orderOrderer = readOrderService.findOrdersByOrderer(orderer);

        return ResponseEntity.ok(orderOrderer);
    }
}
