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

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteDeliveryService implements WriteDeliveryUseCase {

    private final RegisterDeliveryPort registerDeliveryPort;
    private final ReadDeliveryService readDeliveryService;

    LocalDateTime currentTime = LocalDateTime.now();

    @Override
    public DeliveryDTO registerDelivery(RegisterDeliveryCommand command) {
        DeliveryJpaEntity deliveryEntity = registerDeliveryPort.createDelivery(
                Delivery.builder()
                        .deliveryId(command.getDeliveryId())
                        .waybill(command.getWaybill())
                        .deliveryRequest(command.getDeliveryRequest())
                        .receiveMethod(command.getReceiveMethod())
                        .startDate(command.getStartDate())
                        .endDate(Delivery.ETADate(command.getStartDate())) //도착일은 출발일 + 3일
                        .type(command.getType())
                        .progress(command.getProgress())
                        .createdAt(currentTime)
                        .updatedAt(currentTime)
                        .deliveryStatus(Delivery.DeliveryStatus.START)
                        .build()
        );
        return readDeliveryService.toDeliveryDTO(deliveryEntity);
    }
}
