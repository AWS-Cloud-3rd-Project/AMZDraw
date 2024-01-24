package com.amzmall.project.delivery.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class DeliveryStatusDTO {

    private String deliveryId;
    private Delivery.DeliveryStatus deliveryStatus;

    public DeliveryStatusDTO(String deliveryId, Delivery.DeliveryStatus deliveryStatus) {
        this.deliveryId = deliveryId;
        this.deliveryStatus = deliveryStatus;
    }
}


