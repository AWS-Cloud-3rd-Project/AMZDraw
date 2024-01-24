package com.amzmall.project.delivery.domain.events;

import com.amzmall.project.delivery.domain.Delivery;
import lombok.Getter;

public class DeliveryStartedEvent {

    @Getter
    private final Delivery delivery;
    public DeliveryStartedEvent(Delivery delivery) {
        this.delivery = delivery;
    }
}