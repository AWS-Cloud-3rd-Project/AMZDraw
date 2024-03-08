package com.amzmall.project.order.repository;

import com.amzmall.project.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long>{

    Order findOrderByOrderId(String orderId);
    List<Order> findAllByOrderer(String orderer);
}
