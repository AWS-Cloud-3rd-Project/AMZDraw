package com.amzmall.project.order.controller;

import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.order.service.ReadOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "order_find", description="주문조회")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class FindOrderController {

    private final ReadOrderService readOrderService;
    // 주문 전체 조회
    @GetMapping("")
    @Operation(summary="주문 전부 조회", description = "모든 주문을 조회합니다.")
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = readOrderService.findAllOrders();
        return ResponseEntity.ok(orders);

    }

    //주문id 정보 조회
    @GetMapping("/{orderId}")
    @Operation(summary="주문 번호로 조회", description = "주문번호로 주문을 조회합니다")
    public ResponseEntity<Order> findOrderByOrderId(@PathVariable(name = "orderId") String orderId) {
        Order order = readOrderService.findIdOrder(orderId);
        return ResponseEntity.ok(order);
    }

    //주문 목록 조회(내 주문내역 확인에 필요)
    @GetMapping("/{orderer}/orderList")
    @Operation(summary="아이디로 주문 조회", description = "아이디에 해당하는 주문을 모두 조회합니다.")
    public ResponseEntity<List<Order>> findOrdersByOrderer(@PathVariable(name = "orderer") String orderer) {
        List<Order> orderOrderer = readOrderService.findOrdersByOrderer(orderer);

        return ResponseEntity.ok(orderOrderer);
    }
}
