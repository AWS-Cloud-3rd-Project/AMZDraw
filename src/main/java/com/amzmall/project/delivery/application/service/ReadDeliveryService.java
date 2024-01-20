package com.amzmall.project.delivery.application.service;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ReadDeliveryService {

    public DeliveryDTO toDeliveryDTO(DeliveryJpaEntity deliveryEntity) {
        return new DeliveryDTO(
                deliveryEntity.getDeliveryId(),
                deliveryEntity.getDeliveryStatus(),
                deliveryEntity.getDeliveryDepartureDate(),
                deliveryEntity.getDeliveryArrivalDate(),
                deliveryEntity.getReceiver(),
                deliveryEntity.getShippingAddress()

        );
    }
}
