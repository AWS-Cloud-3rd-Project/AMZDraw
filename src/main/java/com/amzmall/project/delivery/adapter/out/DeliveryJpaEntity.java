package com.amzmall.project.delivery.adapter.out;

import com.amzmall.project.delivery.domain.Delivery;
import com.amzmall.project.stock.adapter.out.StockJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
    private Delivery.DeliveryStatus deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockJpaEntity stock;

    public DeliveryJpaEntity(String deliveryId, String waybill, String deliveryRequest, String receiveMethod, LocalDate startDate, LocalDate endDate, String type, String progress, LocalDateTime createdAt, LocalDateTime updatedAt, Delivery.DeliveryStatus deliveryStatus) {
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

