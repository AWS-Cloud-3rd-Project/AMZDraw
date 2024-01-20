package com.amzmall.project.delivery.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NonNull
public class RegisterDeliveryCommand {
    private final String deliveryId;
    private final String deliveryStatus;
    private final LocalDate deliveryDepartureDate;
    private final LocalDate deliveryArrivalDate;
    private final String receiver;
    private final String shippingAddress;

    public RegisterDeliveryCommand(String deliveryId, String deliveryStatus, LocalDate deliveryDepartureDate, LocalDate deliveryArrivalDate, String receiver, String shippingAddress) {
        this.deliveryId = deliveryId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDepartureDate = deliveryDepartureDate;
        this.deliveryArrivalDate = deliveryArrivalDate;
        this.receiver = receiver;
        this.shippingAddress = shippingAddress;
    }
}
