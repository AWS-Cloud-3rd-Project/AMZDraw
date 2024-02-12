package com.amzmall.project.repository;

import com.amzmall.project.domain.entity.CancelPayment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelPaymentRepository extends JpaRepository<CancelPayment, Long> {
	List<CancelPayment> findAllByCustomerEmail(String memberEmail, Pageable pageable);
}
