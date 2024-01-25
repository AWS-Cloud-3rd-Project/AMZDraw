package com.amzmall.project.stock.application.service.eventlistener;

import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.event.OrderCompletedEvent;
import com.amzmall.project.stock.application.service.WriteStockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class StockEventListener {
//
//    private final WriteStockService stockService;
//
////    @EventListener
////    public void onDeliveryReady(OrderCompletedEvent event) {
////        Order order = event.getOrder();
////
////        log.info("received orderCompletedEvent : {}", order);
////
////        stockService.decreaseStock(
////                order.getOrderId(), order.getOrderQuantity());
////    }
////}