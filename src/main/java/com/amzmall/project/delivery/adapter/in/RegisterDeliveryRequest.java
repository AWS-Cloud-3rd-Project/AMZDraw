package com.amzmall.project.delivery.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDeliveryRequest {

    private String deliveryId;
    private int deliveryQuantity;

    private String waybill;
    private String deliveryRequest;
    private String receiveMethod;

    //배송 출발 날짜
    private LocalDate startDate;
    private String type;
    private String progress;
}
