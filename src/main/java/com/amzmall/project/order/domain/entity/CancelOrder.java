package com.amzmall.project.order.domain.entity;

import com.amzmall.project.customer.domain.entity.Customer;
import com.amzmall.project.order.domain.dto.CancelPaymentResDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "cancel_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CancelOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cancel_order_id", nullable = false)
	private int cancelPaymentId;

	@Column(nullable = false)
	private String orderId;					// 우리 결제 고유 번호

	@Column(nullable = false)
	private String paymentKey;				// 토스 결제 고유 번호

	@Column(nullable = false)
	private String orderName;				// 상품명

	@Column(nullable = false)
	private String requestedAt;				// 결제 취소가 일어난 날짜와 시간

	@Column
	private String approvedAt;				// 결제 취소 승인이 일어난 날짜와 시간

	@Column
	private String cardNumber;				// 카드번호

	@Column(nullable = false)
	private String cancelReason;			// 결제 취소 이유

	@Column(nullable = false)
	private String cancelDate;				// 결제 취소 날짜

	@Column(nullable = false)
	private int cancelAmount;				// 결제 취소 금액

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private Timestamp createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Timestamp updatedAt;
;
	@Setter
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;

	public CancelPaymentResDto toDto() {
		return CancelPaymentResDto.builder()
			.cancelPaymentId(cancelPaymentId)
			.orderId(orderId)
			.paymentKey(paymentKey)
			.orderName(orderName)
			.requestedAt(requestedAt)
			.approvedAt(approvedAt)
			.cardNumber(cardNumber)
			.cancelReason(cancelReason)
			.cancelAmount(cancelAmount)
			.build();
	}
}
