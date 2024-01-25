package com.amzmall.project.delivery.application.service.eventlistener;

import com.amzmall.project.delivery.adapter.out.DeliveryJpaEntity;
import com.amzmall.project.delivery.application.port.out.WriteDeliveryPort;
import com.amzmall.project.delivery.application.service.ReadDeliveryService;
import com.amzmall.project.delivery.application.service.WriteDeliveryService;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.event.OrderCompletedEvent;
import com.amzmall.project.stock.application.service.WriteStockService;
import com.amzmall.project.stock.domain.Stock;
import com.amzmall.project.stock.domain.events.StockDecreasedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryProcessListener {

    private final WriteDeliveryPort writeDeliveryPort;

    @EventListener
    public void onDeliveryStarted(OrderCompletedEvent event) {

        OrderJpaEntity orderEntity = event.getOrderJpaEntity();

        Delivery delivery = Delivery.builder()
                .deliveryId(orderEntity.getOrderId())
                .deliveryQuantity(orderEntity.getOrderQuantity())
                .startDate(orderEntity.getOrderDate())
                .endDate(orderEntity.getOrderDate().plusDays(3))
                .deliveryStatus(Delivery.DeliveryStatus.READY)
                .build();

        writeDeliveryPort.createDelivery(delivery);

    }
}