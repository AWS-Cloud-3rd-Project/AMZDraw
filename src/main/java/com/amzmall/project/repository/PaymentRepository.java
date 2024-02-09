package com.amzmall.project.repository;

import com.amzmall.project.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrderId(String orderId);

	List<Payment> findAllByCustomerEmail(String customerEmail);

	Optional<Payment> findByPaymentKey(String paymentKey);
}
