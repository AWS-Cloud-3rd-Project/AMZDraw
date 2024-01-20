package com.amzmall.project.delivery.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDeliveryRequest {
    private String deliveryId;
    private String deliveryStatus;
    private LocalDate deliveryDepartureDate;
    private String receiver;
    private String shippingAddress;
}
