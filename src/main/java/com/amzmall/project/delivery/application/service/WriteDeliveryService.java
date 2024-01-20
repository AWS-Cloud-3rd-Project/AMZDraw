package com.amzmall.project.delivery.application.service;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.application.port.in.RegisterDeliveryCommand;
import com.amzmall.project.delivery.application.port.in.WriteDeliveryUseCase;
import com.amzmall.project.delivery.application.port.out.RegisterDeliveryPort;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteDeliveryService implements WriteDeliveryUseCase {

    private final RegisterDeliveryPort registerDeliveryPort;
    private final ReadDeliveryService readDeliveryService;

    @Override
    public DeliveryDTO registerDelivery(RegisterDeliveryCommand command) {
        DeliveryJpaEntity deliveryEntity = registerDeliveryPort.createDelivery(
                Delivery.builder()
                        .deliveryId(command.getDeliveryId())
                        .deliveryStatus(command.getDeliveryStatus())
                        .deliveryDepartureDate(command.getDeliveryDepartureDate())
                        .deliveryArrivalDate(Delivery.ETADate(command.getDeliveryDepartureDate()))
                        .receiver(command.getReceiver())
                        .shippingAddress(command.getShippingAddress())
                        .build()
        );
        return readDeliveryService.toDeliveryDTO(deliveryEntity);
    }
}
