package com.amzmall.project.delivery.application.port.in;

import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import com.amzmall.project.delivery.domain.DeliveryStatusDTO;
import org.springframework.stereotype.Component;

public interface WriteDeliveryUseCase {
    DeliveryDTO registerDelivery(RegisterDeliveryCommand command);
    DeliveryStatusDTO changeStatusToStart(String deliveryId);
    DeliveryStatusDTO changeStatusToComplete(String deliveryId);
}
