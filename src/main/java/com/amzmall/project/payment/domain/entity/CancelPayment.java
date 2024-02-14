package com.amzmall.project.payment.domain.entity;

import com.amzmall.project.payment.domain.dto.CancelPaymentResDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CancelPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cancel_payment_id", nullable = false)
	private Long cancelPaymentId;

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
	private String cardCompany;				// 카드사

	@Column
	private String cardNumber;				// 카드번호

	@Column
	private String cardReceiptUrl;			// 결제 취소 영수증

	@Column(nullable = false)
	private String cancelReason;			// 결제 취소 이유

	@Column(nullable = false)
	private String cancelDate;				// 결제 취소 날짜

	@Column(nullable = false)
	private Long cancelAmount;				// 결제 취소 금액

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private Timestamp createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Timestamp updatedAt;
;
	@Setter
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Customer customer;

	public CancelPaymentResDto toCancelPaymentResDto() {
		return CancelPaymentResDto.builder()
			.cancelPaymentId(cancelPaymentId)
			.orderId(orderId)
			.paymentKey(paymentKey)
			.orderName(orderName)
			.requestedAt(requestedAt)
			.approvedAt(approvedAt)
			.cardCompany(cardCompany)
			.cardNumber(cardNumber)
			.receiptUrl(cardReceiptUrl)
			.cancelReason(cancelReason)
			.cancelAmount(cancelAmount)
			.build();
	}
}
