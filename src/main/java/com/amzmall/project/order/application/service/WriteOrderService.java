package com.amzmall.project.order.application.service;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import com.amzmall.project.order.application.port.in.WriteOrderUseCase;
import com.amzmall.project.order.application.port.in.RegisterOrderCommand;
import com.amzmall.project.order.application.port.out.ReadOrderPort;
import com.amzmall.project.order.application.port.out.WriteOrderPort;
import com.amzmall.project.order.domain.Order;
import com.amzmall.project.order.domain.OrderDTO;
import com.amzmall.project.order.domain.OrderStatusDTO;
import com.amzmall.project.order.domain.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WriteOrderService implements WriteOrderUseCase {

    private final WriteOrderPort writeOrderPort;
    private final ReadOrderPort readOrderPort;

    private final ReadOrderService readService;

    private final ApplicationEventPublisher eventPublisher;

    LocalDate currentDate = LocalDate.now();

    @Override
    public OrderDTO registerOrder(RegisterOrderCommand command) {

        Order order = Order.builder()
                .orderId(command.getOrderId())
                .orderStatus(Order.OrderStatus.ORDER_WAITING)
                .orderDate(currentDate)
                .orderer(command.getOrderer())
                .orderQuantity(command.getOrderQuantity())
                .orderAmount(command.getOrderAmount())
                .build();

        OrderJpaEntity orderJpaEntity = writeOrderPort.createOrder(order);
        return readService.toOrderDto(orderJpaEntity);
    }

    @Override
    public OrderStatusDTO completeOrder(String orderId) {
        OrderJpaEntity orderEntity = readOrderPort.findByOrderId(orderId);

        if (orderEntity.getOrderStatus() == Order.OrderStatus.ORDER_WAITING) {
            orderEntity.setOrderStatus(Order.OrderStatus.ORDER_COMPLETED);
            orderEntity.setOrderDate(currentDate);
            OrderJpaEntity completedOrder = writeOrderPort.updateOrder(orderEntity);

            eventPublisher.publishEvent(new OrderCompletedEvent(completedOrder));

            return readService.toOrderStatusDto(completedOrder);

        } else {
            log.info("orderEntity.getOrderStatus() : {}", orderEntity.getOrderStatus());
            throw new IllegalStateException("주문이 '대기중' 상태에서만 완료될 수 있습니다.");
        }
    }
}
