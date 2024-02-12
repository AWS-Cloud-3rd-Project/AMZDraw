package com.amzmall.project.repository;

import com.amzmall.project.domain.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrderId(String orderId);

	Optional<Payment> findByPaymentKey(String paymentKey);

	List<Payment> findAllByCustomerEmail(String email, Pageable pageable);

	Optional<Payment> findByCustomerEmailAndOrderId(String email, String OrderId);
}
