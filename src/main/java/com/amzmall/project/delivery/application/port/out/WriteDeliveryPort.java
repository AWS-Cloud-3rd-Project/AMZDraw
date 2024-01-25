package com.amzmall.project.delivery.application.port.out;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.stock.domain.Stock;
import org.springframework.stereotype.Component;

@Component
public interface WriteDeliveryPort {

    DeliveryJpaEntity createDelivery(Delivery delivery);
    DeliveryJpaEntity updateDelivery(DeliveryJpaEntity deliveryEntity);
}
