package com.amzmall.project.delivery.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeliveryRepository extends JpaRepository<DeliveryJpaEntity, Long> {
}
