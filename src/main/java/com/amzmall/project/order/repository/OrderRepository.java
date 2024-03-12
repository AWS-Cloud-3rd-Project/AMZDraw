package com.amzmall.project.order.repository;

import com.amzmall.project.order.domain.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findByOrderId(String orderId);

	Optional<Order> findByPaymentKey(String paymentKey);

	List<Order> findAllByUsersEmail(String email, Pageable pageable);

	Optional<Order> findByUsersEmailAndOrderId(String customerEmail, String OrderId);
}
