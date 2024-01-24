package com.amzmall.project.order.domain.event;

import com.amzmall.project.order.domain.Order;
import lombok.Getter;

public class OrderCompletedEvent {

    @Getter
    private final Order order;

    public OrderCompletedEvent(Order order) {
        this.order = order;
    }
}
