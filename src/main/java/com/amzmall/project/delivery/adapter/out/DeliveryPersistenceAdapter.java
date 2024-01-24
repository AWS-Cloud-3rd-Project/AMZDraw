package com.amzmall.project.delivery.adapter.out;

import com.amzmall.project.delivery.application.port.out.ReadDeliveryPort;
import com.amzmall.project.delivery.application.port.out.WriteDeliveryPort;
import com.amzmall.project.delivery.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryPersistenceAdapter implements WriteDeliveryPort, ReadDeliveryPort {
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

    @Override
    public void changeStatusToStart(Delivery delivery) {
        deliveryRepository.findById(delivery.getDeliveryId())
                .ifPresent(deliveryJpaEntity -> {
                    deliveryJpaEntity.setDeliveryStatus(Delivery.DeliveryStatus.START);
                    deliveryRepository.save(deliveryJpaEntity);
                });
    }

    @Override
    public void changeStatusToComplete(Delivery delivery) {
        deliveryRepository.findById(delivery.getDeliveryId())
                .ifPresent(deliveryJpaEntity -> {
                    deliveryJpaEntity.setDeliveryStatus(Delivery.DeliveryStatus.COMPLETE);
                    deliveryRepository.save(deliveryJpaEntity);
                });
    }

    @Override
    public DeliveryJpaEntity findByDeliveryId(String deliveryId) {
        return deliveryRepository.findByDeliveryId(deliveryId);
    }
}