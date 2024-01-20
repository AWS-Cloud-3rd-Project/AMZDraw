package com.amzmall.project.delivery.application.port.in;

import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import org.springframework.stereotype.Component;

@Component
public interface WriteDeliveryUseCase {

    DeliveryDTO registerDelivery(RegisterDeliveryCommand command);
}
