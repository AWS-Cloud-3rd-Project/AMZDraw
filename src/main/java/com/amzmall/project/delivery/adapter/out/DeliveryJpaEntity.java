package com.amzmall.project.delivery.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public DeliveryJpaEntity(String deliveryId, String deliveryStatus, LocalDate deliveryDepartureDate, LocalDate deliveryArrivalDate, String receiver, String shippingAddress) {
        this.deliveryId = deliveryId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDepartureDate = deliveryDepartureDate;
        this.deliveryArrivalDate = deliveryArrivalDate;
        this.receiver = receiver;
        this.shippingAddress = shippingAddress;
    }
}
