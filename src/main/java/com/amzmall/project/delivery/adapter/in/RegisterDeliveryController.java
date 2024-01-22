package com.amzmall.project.delivery.adapter.in;

import com.amzmall.project.delivery.application.port.in.RegisterDeliveryCommand;
import com.amzmall.project.delivery.application.port.in.WriteDeliveryUseCase;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class RegisterDeliveryController {

    private final WriteDeliveryUseCase writeDeliveryUseCase;

    @PostMapping("/register")
    public DeliveryDTO registerDelivery(@RequestBody RegisterDeliveryRequest deliveryRequest) {

        RegisterDeliveryCommand command = RegisterDeliveryCommand.builder()
                .deliveryId(deliveryRequest.getDeliveryId())
                .waybill(deliveryRequest.getWaybill())
                .deliveryRequest(deliveryRequest.getDeliveryRequest())
                .receiveMethod(deliveryRequest.getReceiveMethod())
                .startDate(deliveryRequest.getStartDate())
                .type(deliveryRequest.getType())
                .progress(deliveryRequest.getProgress())
                .build();
        return writeDeliveryUseCase.registerDelivery(command);
    }
}
