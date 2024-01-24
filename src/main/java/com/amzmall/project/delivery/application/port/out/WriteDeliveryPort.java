package com.amzmall.project.delivery.application.port.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.domain.Delivery;
import org.springframework.stereotype.Component;

@Component
public interface WriteDeliveryPort {

    DeliveryJpaEntity createDelivery(Delivery delivery);

    void changeStatusToStart(Delivery delivery);
    void changeStatusToComplete(Delivery delivery);
}
