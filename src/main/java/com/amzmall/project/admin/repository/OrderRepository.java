package com.amzmall.project.admin.repository;

import com.amzmall.project.admin.domain.order.Order;
import com.amzmall.project.admin.domain.order.OrderDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            "SELECT new com.amzmall.project.admin.domain.order.OrderDetailView(o.orderId, o.customerId, c.customerName, o.amount, o.orderStatus, o.payType, o.createdAt) " +
                    "FROM Order o JOIN Customer c ON o.customerId = c.customerId"
    )
    List<OrderDetailView> findAllOrderDetailView();
}