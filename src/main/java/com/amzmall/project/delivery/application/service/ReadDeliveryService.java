package com.amzmall.project.delivery.application.service;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.application.port.out.ReadDeliveryPort;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import com.amzmall.project.delivery.domain.DeliveryStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ReadDeliveryService {

    private final ReadDeliveryPort readDeliveryPort;

    //배송 정보 조회
    public DeliveryDTO findByDeliveryId(String deliveryId) {
        DeliveryJpaEntity deliveryEntity = readDeliveryPort.findByDeliveryId(deliveryId);
        return toDeliveryDTO(deliveryEntity);
    }
    public DeliveryDTO toDeliveryDTO(DeliveryJpaEntity deliveryEntity) {
        return new DeliveryDTO(
                deliveryEntity.getDeliveryId(),
                deliveryEntity.getWaybill(),
                deliveryEntity.getDeliveryRequest(),
                deliveryEntity.getReceiveMethod(),
                deliveryEntity.getStartDate(),
                deliveryEntity.getEndDate(),
                deliveryEntity.getType(),
                deliveryEntity.getProgress(),
                deliveryEntity.getCreatedAt(),
                deliveryEntity.getUpdatedAt(),
                deliveryEntity.getDeliveryStatus()
        );
    }
    public DeliveryStatusDTO toDeliveryStatusDTO(DeliveryJpaEntity deliveryEntity) {
        return new DeliveryStatusDTO(
                deliveryEntity.getDeliveryId(),
                deliveryEntity.getDeliveryStatus()
        );
    }
}
