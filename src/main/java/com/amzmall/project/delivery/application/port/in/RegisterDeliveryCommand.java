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
    private String deliveryId;

    private String waybill;
    private String deliveryRequest;
    private String receiveMethod;

    //배송 출발 날짜
    private LocalDate startDate;
    private String type;
    private String progress;

    public RegisterDeliveryCommand(String deliveryId, String waybill, String deliveryRequest, String receiveMethod, LocalDate startDate, String type, String progress) {
        this.deliveryId = deliveryId;
        this.waybill = waybill;
        this.deliveryRequest = deliveryRequest;
        this.receiveMethod = receiveMethod;
        this.startDate = startDate;
        this.type = type;
        this.progress = progress;
    }
}
