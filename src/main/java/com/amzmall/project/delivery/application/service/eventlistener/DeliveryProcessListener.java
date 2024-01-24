package com.amzmall.project.delivery.application.service.eventlistener;

import com.amzmall.project.delivery.application.port.out.WriteDeliveryPort;
import com.amzmall.project.delivery.application.service.WriteDeliveryService;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.event.OrderCompletedEvent;
import com.amzmall.project.stock.application.service.WriteStockService;
import com.amzmall.project.stock.domain.Stock;
import com.amzmall.project.stock.domain.events.StockDecreasedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryProcessListener {

    private final WriteDeliveryService writeService;

    @EventListener
    public void onDeliveryStarted(StockDecreasedEvent event) {
        Stock stock = event.getStock();
        writeService.changeStatusToStart(stock.getStockId());
    }
}