package com.amzmall.project.order.adapter.out;

import com.amzmall.project.order.domain.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    OrderJpaEntity findByOrderId(String orderId);
}
