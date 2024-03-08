package com.amzmall.project.order.domain.entity;

import com.amzmall.project.customer.domain.entity.Customer;
import com.amzmall.project.util.config.TimeConfig;
import com.amzmall.project.order.domain.dto.PaymentResDto;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Component
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private int seq;

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
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
        name = "order_product", // 중간 테이블 이름
        joinColumns = @JoinColumn(name = "order_id"), // 주문 테이블과 연결할 컬럼
        inverseJoinColumns = @JoinColumn(name = "product_id") // 상품 테이블과 연결할 컬럼
    )

    private List<Product> products = new ArrayList<>();
    public void addProduct(Product product) {
        products.add(product);
        product.getOrders().add(this);
    }

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
