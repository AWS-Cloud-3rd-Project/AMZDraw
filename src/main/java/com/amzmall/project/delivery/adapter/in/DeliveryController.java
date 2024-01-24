package com.amzmall.project.delivery.adapter.in;

import com.amzmall.project.delivery.application.port.in.RegisterDeliveryCommand;
import com.amzmall.project.delivery.application.port.in.WriteDeliveryUseCase;
import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.delivery.domain.DeliveryDTO;
import com.amzmall.project.delivery.domain.DeliveryStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final WriteDeliveryUseCase writeDeliveryUseCase;

    @PostMapping("/register")
    public DeliveryDTO registerDelivery(@RequestBody RegisterDeliveryRequest deliveryRequest) {

        RegisterDeliveryCommand command = RegisterDeliveryCommand.builder()
                .deliveryId(deliveryRequest.getDeliveryId())
                .deliveryQuantity(deliveryRequest.getDeliveryQuantity())
                .waybill(deliveryRequest.getWaybill())
                .deliveryRequest(deliveryRequest.getDeliveryRequest())
                .receiveMethod(deliveryRequest.getReceiveMethod())
                .startDate(deliveryRequest.getStartDate())
                .type(deliveryRequest.getType())
                .progress(deliveryRequest.getProgress())
                .build();
        return writeDeliveryUseCase.registerDelivery(command);
    }

    @PostMapping("/{deliveryId}/start")
    public ResponseEntity<DeliveryStatusDTO> startDelivery(@PathVariable String deliveryId) {

        DeliveryStatusDTO StatusDTO = writeDeliveryUseCase.changeStatusToStart(deliveryId);
        return ResponseEntity.ok(StatusDTO);
    }

    @PostMapping("/{deliveryId}/complete")
    public ResponseEntity<DeliveryStatusDTO> completeDelivery(@PathVariable String deliveryId) {

        DeliveryStatusDTO StatusDTO = writeDeliveryUseCase.changeStatusToComplete(deliveryId);
        return ResponseEntity.ok(StatusDTO);
    }
}
