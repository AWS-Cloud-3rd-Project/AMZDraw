package com.amzmall.project.repository;

import com.amzmall.project.domain.entity.Payment;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByOrderId(String orderId); // 주문 Id 조회 
	Optional<Payment> findByPaymentKeyAndUser_Email(String paymentKey, String email); // paymentKey와 고객 email로 Payment 객체 조회
	Slice<Payment> findAllByUser_Email(String email, Pageable pageable);	// 내 주문 전체 조회
}
