package com.amzmall.project.order.domain.entity;

import com.amzmall.project.users.domain.entity.Users;
import com.amzmall.project.order.domain.dto.OrderResDto;
import com.amzmall.project.order.domain.dto.PaymentDto;
import com.amzmall.project.product.domain.entity.Product;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private int seq;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
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

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(name = "payment_key")
    private String paymentKey;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Setter
    @Column(name = "is_pay_success")
    private boolean isPaySuccess;			// 결제 성공 여부

    @Setter
    @Column(name = "is_pay_cancled")
    private boolean isPayCancled;				// 결제 취소 여부

    @Setter
    @Column
    private String payFailReason;			// 결제 실패 이유

    @Setter
    @Column
    private String cardNumber;				// 카드 번호

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderResDto toEntity(){
        return OrderResDto.builder()
            .amount(amount)
            .orderId(orderId)
            .productId(product.getId())
            .orderName(orderName)
            .customerName(customerName)
            .customerEmail(customerEmail)
            .customerAddress(customerAddress)
            .customerPhone(customerPhone)
            .paymentKey(paymentKey)
            .createdAt(String.valueOf(createdAt))
            .build();
    }

    public PaymentDto toDto() {
        return PaymentDto.builder()
            .paymentType(paymentType.getName())
            .amount(amount)
            .cardNumber(cardNumber)
            .orderId(orderId)
            .orderName(orderName)
            .customerEmail(customerEmail)
            .customerName(customerName)
            .customerAddress(customerAddress)
            .paymentKey(paymentKey)
            .isPaySuccess(isPaySuccess)
            .isPayCancled(isPayCancled)
            .payFailReason(payFailReason)
            .createdAt(String.valueOf(createdAt))
            .build();
    }
}
