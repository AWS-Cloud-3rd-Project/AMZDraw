package com.amzmall.project.order.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    OrderJpaEntity findByOrderId(String orderId);
}
