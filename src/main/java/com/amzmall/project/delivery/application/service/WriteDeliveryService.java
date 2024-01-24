package com.amzmall.project.delivery.application.service;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.application.port.in.RegisterDeliveryCommand;
import com.amzmall.project.delivery.application.port.in.WriteDeliveryUseCase;
import com.amzmall.project.delivery.application.port.out.ReadDeliveryPort;
import com.amzmall.project.delivery.application.port.out.WriteDeliveryPort;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import com.amzmall.project.delivery.domain.DeliveryStatusDTO;
import com.amzmall.project.delivery.domain.events.DeliveryCompletedEvent;
import com.amzmall.project.delivery.domain.events.DeliveryStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WriteDeliveryService implements WriteDeliveryUseCase {

    private final WriteDeliveryPort writeDeliveryPort;
    private final ReadDeliveryPort readDeliveryPort;

    private final ReadDeliveryService readDeliveryService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public DeliveryDTO registerDelivery(RegisterDeliveryCommand command) {

        Delivery delivery = Delivery.builder()
                .deliveryId(command.getDeliveryId())
                .deliveryQuantity(command.getDeliveryQuantity())
                .waybill(command.getWaybill())
                .deliveryRequest(command.getDeliveryRequest())
                .receiveMethod(command.getReceiveMethod())
                .startDate(command.getStartDate())
                .endDate(ETADate(command.getStartDate())) //도착일은 출발일 + 3일
                .type(command.getType())
                .progress(command.getProgress())
                .deliveryStatus(Delivery.DeliveryStatus.READY)
                .build();

        DeliveryJpaEntity deliveryEntity = writeDeliveryPort.createDelivery(delivery);
        eventPublisher.publishEvent(new DeliveryCompletedEvent(delivery));

        return readDeliveryService.toDeliveryDTO(deliveryEntity);
    }

    @Override
    public DeliveryStatusDTO changeStatusToStart(String deliveryId) {

        DeliveryJpaEntity deliveryEntity = readDeliveryPort.findByDeliveryId(deliveryId);

        Delivery delivery = Delivery.builder()
                .deliveryId(deliveryEntity.getDeliveryId())
                .deliveryStatus(Delivery.DeliveryStatus.READY)
                .build();

        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.READY) {
            delivery.startDelivery();

            eventPublisher.publishEvent(new DeliveryStartedEvent(delivery));

            // 상태 변경 후 다시 조회
            DeliveryJpaEntity updatedDeliveryEntity = readDeliveryPort.findByDeliveryId(deliveryId);
            return readDeliveryService.toDeliveryStatusDTO(deliveryEntity);
        } else {
            log.info("delivery.getDeliveryStatus() = {}", delivery.getDeliveryStatus());
            throw new IllegalArgumentException("배송 시작 이벤트는 READY 상태에서만 발생할 수 있습니다.");
        }
    }

    @Override
    public DeliveryStatusDTO changeStatusToComplete(String deliveryId) {
        DeliveryJpaEntity deliveryEntity = readDeliveryPort.findByDeliveryId(deliveryId);

        Delivery delivery = Delivery.builder()
                .deliveryId(deliveryEntity.getDeliveryId())
                .deliveryStatus(Delivery.DeliveryStatus.START)
                .build();

        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.START) {
            delivery.completeDelivery();

            eventPublisher.publishEvent(new DeliveryCompletedEvent(delivery));

            // 상태 변경 후 다시 조회
            DeliveryJpaEntity updatedDeliveryEntity = readDeliveryPort.findByDeliveryId(deliveryId);
            return readDeliveryService.toDeliveryStatusDTO(deliveryEntity);
        } else {
            log.info("delivery.getDeliveryStatus() = {}", delivery.getDeliveryStatus());
            throw new IllegalArgumentException("배송 완료 이벤트는 START 상태에서만 발생할 수 있습니다.");
        }
    }

    //배송 도착 날짜는 출발일 + 3일 설정
    private static LocalDate ETADate(LocalDate startDate) {
        return startDate.plusDays(3);
    }
}
