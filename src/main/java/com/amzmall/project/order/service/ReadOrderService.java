package com.amzmall.project.order.service;

import com.amzmall.project.order.domain.entity.Order;
import com.amzmall.project.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadOrderService  {

    private final OrderRepository orderRepository;

    //주문 전체 조회
    public List<Order> findAllOrders(){
        List<Order> orders = orderRepository.findAll();//가장 최근의 내용을
        return orders;
    }


    //주문id 단건 조회

    public Order findIdOrder(String orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        return order;
    }

    // 회원주문 정보 조회
    public List<Order> findOrdersByOrderer(String orderer) {
        List<Order> orders= orderRepository.findAllByOrderer(orderer);
        return orders;
    }



//    public OrderDTO toOrderDto(Order orderEntity) {
//        return new OrderDTO(
//                orderEntity.getOrderId(),
//                orderEntity.getOrderStatus(),
//                orderEntity.getOrderDate(),
//                orderEntity.getOrderer(),
//                orderEntity.getOrderAmount()
//        );
//    }

}
