package com.amzmall.project.order.domain.event;

import com.amzmall.project.order.adapter.out.OrderJpaEntity;
import lombok.Getter;

public class OrderCompletedEvent {

    @Getter
    private final OrderJpaEntity orderJpaEntity;

    public OrderCompletedEvent(OrderJpaEntity orderJpaEntity) {
        this.orderJpaEntity = orderJpaEntity;
    }
}
