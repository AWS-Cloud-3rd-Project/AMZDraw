package com.amzmall.project.delivery.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Delivery {

    //배송 번호
    private String deliveryId;
    //배송 상태
    private String deliveryStatus; // TODO enum으로 변경 (배송 대기, 배송 완료, 배송 취소)
    //배송 출발 날짜
    private LocalDate deliveryDepartureDate;
    //배송 도착 날짜는 출발일 + 3일
    private LocalDate deliveryArrivalDate;
    //받는 사람
    private String receiver;
    //배송지 주소
    private String shippingAddress;

    //배송 도착 날짜는 출발일 + 3일 설정
    public static LocalDate ETADate(LocalDate deliveryDepartureDate) {
        return deliveryDepartureDate.plusDays(3);
    }

    @Builder
    public Delivery(String deliveryId, String deliveryStatus, LocalDate deliveryDepartureDate, LocalDate deliveryArrivalDate, String receiver, String shippingAddress) {
        this.deliveryId = deliveryId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDepartureDate = deliveryDepartureDate;
        this.deliveryArrivalDate = deliveryArrivalDate;
        this.receiver = receiver;
        this.shippingAddress = shippingAddress;
    }
}
