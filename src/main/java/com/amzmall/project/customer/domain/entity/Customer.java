package com.amzmall.project.customer.domain.entity;

import com.amzmall.project.customer.domain.dto.CustomerDto;

import com.amzmall.project.payment.domain.entity.CancelPayment;
import com.amzmall.project.payment.domain.entity.Payment;
import com.amzmall.project.qna.domain.entity.Question;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birth")
    private java.sql.Date birth;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code", length = 5)
    private String zipCode;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private ECommerceRole role;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<Payment> payments = new ArrayList<>();
    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setCustomer(this);
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<CancelPayment> cancelPayments = new ArrayList<>();
    public void addCancelPayment(CancelPayment cancelPayment) {
        this.cancelPayments.add(cancelPayment);
        cancelPayment.setCustomer(this);
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default    // 빌더 사용시 필드에 객체 타입이 있다면 반드시 사용
    private List<Question> questions = new ArrayList<>();
    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setCustomer(this);
    }

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .customerId(customerId)
                .email(email)
                .name(name)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}

