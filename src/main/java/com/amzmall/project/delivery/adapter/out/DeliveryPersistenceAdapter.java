package com.amzmall.project.delivery.adapter.out;

import com.amzmall.project.delivery.application.port.out.RegisterDeliveryPort;
import com.amzmall.project.delivery.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryPersistenceAdapter implements RegisterDeliveryPort {
    private final SpringDataDeliveryRepository deliveryRepository;
    @Override
    public DeliveryJpaEntity createDelivery(Delivery delivery) {
        return deliveryRepository.save(
                new DeliveryJpaEntity(
                        delivery.getDeliveryId(),
                        delivery.getWaybill(),
                        delivery.getDeliveryRequest(),
                        delivery.getReceiveMethod(),
                        delivery.getStartDate(),
                        delivery.getEndDate(),
                        delivery.getType(),
                        delivery.getProgress(),
                        delivery.getCreatedAt(),
                        delivery.getUpdatedAt(),
                        delivery.getDeliveryStatus()
                )
        );
    }
}