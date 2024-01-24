package com.amzmall.project.delivery.application.port.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;

import java.util.List;

public interface ReadDeliveryPort {

    DeliveryJpaEntity findByDeliveryId(String deliveryId);
}
