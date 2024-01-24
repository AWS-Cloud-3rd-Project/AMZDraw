package com.amzmall.project.stock.application.service.eventlistener;

import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.events.DeliveryStartedEvent;
import com.amzmall.project.stock.application.service.WriteStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final WriteStockService stockService;

    @EventListener
    public void onDeliveryStarted(DeliveryStartedEvent event) {
        Delivery delivery = event.getDelivery();
        // 재고 감소 로직 호출
        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.READY) {
            stockService.decreaseStock(delivery.getDeliveryId(), delivery.getDeliveryQuantity());
        } else {
            throw new IllegalArgumentException("배송 시작 이벤트는 READY 상태에서만 발생할 수 있습니다.");
        }
    }
}