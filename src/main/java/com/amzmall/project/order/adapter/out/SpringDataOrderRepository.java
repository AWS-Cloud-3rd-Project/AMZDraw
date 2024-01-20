package com.amzmall.project.order.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    OrderJpaEntity findByOrderId(String orderId);

    List<OrderJpaEntity> findAllByOrderer(String orderer);
}
