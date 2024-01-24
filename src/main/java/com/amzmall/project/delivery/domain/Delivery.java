package com.amzmall.project.delivery.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Delivery {

    public enum DeliveryStatus {
        READY, START, COMPLETE
    }

    private String deliveryId;
    //배송 수량
    private int deliveryQuantity;

    private String waybill;
    private String deliveryRequest;
    private String receiveMethod;

    //배송 출발 날짜
    private LocalDate startDate;
    //배송 도착 날짜는 출발일 + 3일
    private LocalDate endDate;
    private String type;
    private String progress;
    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //배송 상태
    private DeliveryStatus deliveryStatus; // TODO enum으로 변경 (배송 준비, 배송 시작, 배송 완료)

    //배송 상태 변경
    public Delivery startDelivery() {
        if (this.deliveryStatus == DeliveryStatus.READY) {
            this.deliveryStatus = DeliveryStatus.START;
            return this;
        } else {
            throw new IllegalStateException("배송은 '배송 준비' 상태에서만 시작될 수 있습니다.");
        }
    }
    public Delivery completeDelivery() {
        // 배송 상태가 '배송 중'인 경우에만 '배송 완료'로 변경 가능
        if (this.deliveryStatus == DeliveryStatus.START) {
            this.deliveryStatus = DeliveryStatus.COMPLETE;
            return this;
        } else {
            throw new IllegalStateException("배송은 '배송 중' 상태에서만 완료될 수 있습니다.");
        }
    }

    @Builder
    public Delivery(String deliveryId, String waybill, String deliveryRequest, String receiveMethod, LocalDate startDate, LocalDate endDate, String type, String progress, LocalDateTime createdAt, LocalDateTime updatedAt, int deliveryQuantity, DeliveryStatus deliveryStatus) {
        this.deliveryId = deliveryId;
        this.deliveryQuantity = deliveryQuantity;
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
