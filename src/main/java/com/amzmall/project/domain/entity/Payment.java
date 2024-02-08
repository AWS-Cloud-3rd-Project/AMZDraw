package com.amzmall.project.domain.entity;

import com.amzmall.project.config.TimeConfig;
import com.amzmall.project.domain.dto.PaymentResDto;
import lombok.*;

import jakarta.persistence.*;
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
	private Long paymentId;

	@Enumerated(EnumType.STRING)
	@Column(name = "pay_type", nullable = false)
	private PayType payType;

	@Column(name = "amount", nullable = false)
	private Long amount;

	@Column(name = "order_name", nullable = false)
	private String orderName;

	@Column(name = "order_id", nullable = false)
	private String orderId;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "payment_key", nullable = false)
	private String paymentKey;

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private Timestamp createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	private boolean paySuccessYn;

//	@Setter
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//	private Customer customer;

	public PaymentResDto toPaymentDto(){
		return PaymentResDto.builder()
				.payType(payType.getName())
				.amount(amount)
				.orderId(orderId)
				.orderName(orderName)
				.customerName(customerName)
				.customerEmail(customerEmail)
				.paymentKey(paymentKey)
				.createdDate(new TimeConfig().getNowTime())
				.build();
	}
}
