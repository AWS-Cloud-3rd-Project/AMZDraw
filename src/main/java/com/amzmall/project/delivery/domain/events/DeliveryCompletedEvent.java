package com.amzmall.project.delivery.domain.events;

import com.amzmall.project.delivery.domain.Delivery;
import lombok.Getter;

public class DeliveryCompletedEvent {

    @Getter
    private final Delivery delivery;
    public DeliveryCompletedEvent(Delivery delivery) {
        this.delivery = delivery;
    }
}
