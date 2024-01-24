package com.amzmall.project.delivery.application.port.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public interface ReadDeliveryPort {

    DeliveryJpaEntity findByDeliveryId(String deliveryId);
}
