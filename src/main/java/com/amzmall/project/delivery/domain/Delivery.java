package com.amzmall.project.delivery.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Delivery {

    public enum DeliveryStatus {
        READY, START, IN_PROGRESS, COMPLETE
    }

    private String deliveryId;

    private String waybill;
    private String deliveryRequest;
    private String receiveMethod;

    //배송 출발 날짜
    private LocalDate startDate;
    //배송 도착 날짜는 출발일 + 3일
    private LocalDate endDate;
    private String type;
    private String progress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //배송 상태
    private DeliveryStatus deliveryStatus; // TODO enum으로 변경 (배송 준비, 배송 시작, 배송 중, 배송 완료)

    //배송 도착 날짜는 출발일 + 3일 설정
    public static LocalDate ETADate(LocalDate startDate) {
        return startDate.plusDays(3);
    }

    @Builder
    public Delivery(String deliveryId, String waybill, String deliveryRequest, String receiveMethod, LocalDate startDate, LocalDate endDate, String type, String progress, LocalDateTime createdAt, LocalDateTime updatedAt, DeliveryStatus deliveryStatus) {
        this.deliveryId = deliveryId;
        this.waybill = waybill;
        this.deliveryRequest = deliveryRequest;
        this.receiveMethod = receiveMethod;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.progress = progress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deliveryStatus = deliveryStatus;
    }
}
