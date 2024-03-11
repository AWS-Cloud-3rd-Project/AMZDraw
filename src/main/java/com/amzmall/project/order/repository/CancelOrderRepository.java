package com.amzmall.project.order.repository;

import com.amzmall.project.order.domain.entity.CancelOrder;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelOrderRepository extends JpaRepository<CancelOrder, Long> {
	List<CancelOrder> findAllByCustomerEmail(String memberEmail, Pageable pageable);
}
