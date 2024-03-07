package com.amzmall.project.payment.domain.entity;

import com.amzmall.project.customer.domain.entity.Customer;
import com.amzmall.project.config.TimeConfig;
import com.amzmall.project.payment.domain.dto.PaymentResDto;
import com.amzmall.project.payment.domain.dto.PaymentDto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Component
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private int paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PAYMENT_TYPE paymentType;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "payment_key")
    private String paymentKey;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Setter
    @Column(nullable = false)
    private boolean isPaySuccess;			// 결제 성공 여부

    @Setter
    @Column(nullable = false)
    private boolean isPayCancled;				// 결제 취소 여부

    @Setter
    @Column
    private String payFailReason;			// 결제 실패 이유

    @Setter
    @Column
    private String cardCompany;				// 카드사

    @Setter
    @Column
    private String cardNumber;				// 카드 번호

    @Setter
    @Column
    private String cardReceiptUrl;			// 영수증 링크


    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    public PaymentResDto toPaymentDto(){
        return PaymentResDto.builder()
            .paymentType(paymentType.getName())
            .amount(amount)
            .orderId(orderId)
            .orderName(orderName)
            .customerName(customerName)
            .customerEmail(customerEmail)
            .paymentKey(paymentKey)
            .createdDate(new TimeConfig().getNowTime())
            .build();
    }

    public PaymentDto toDto() {
        return PaymentDto.builder()
            .paymentType(paymentType.getName())
            .amount(amount)
            .cardCompany(cardCompany)
            .cardNumber(cardNumber)
            .orderId(orderId)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .paymentKey(paymentKey)
            .isPaySuccess(isPaySuccess)
            .isPayCancled(isPayCancled)
            .payFailReason(payFailReason)
            .createDate(new TimeConfig().getNowTime())
            .build();
    }
}
