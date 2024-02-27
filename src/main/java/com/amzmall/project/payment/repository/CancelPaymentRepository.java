package com.amzmall.project.payment.repository;

import com.amzmall.project.payment.domain.entity.CancelPayment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancelPaymentRepository extends JpaRepository<CancelPayment, Long> {
	List<CancelPayment> findAllByCustomerEmail(String memberEmail, Pageable pageable);
}
