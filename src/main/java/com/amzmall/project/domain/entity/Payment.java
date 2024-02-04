package com.amzmall.project.domain.entity;
import com.amzmall.project.domain.dto.PaymentResDto;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id", nullable = false, unique = true)
	private Long paymentId;
	@Column(nullable = false, name = "pay_type")
	@Enumerated(EnumType.STRING)
	private PayType payType;
	@Column(nullable = false, name = "pay_amount")
	private Long amount;
	@Column(nullable = false, name = "pay_name")
	private String orderName;
	@Column(nullable = false, name = "order_id")
	private String orderId;

	private boolean paySuccessYN;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user")
	private User user; //회원과의 관계 (회원은 여러 결제를 가질 수 있음)
	@Column
	private String paymentKey;
	@Column
	private String failReason;

	@Column
	private boolean cancelYN;
	@Column
	private String cancelReason;

	@Column(nullable = false, name = "created_at")
	private Timestamp createdAt;

	@Column(nullable = false, name = "updated_at")
	private Timestamp updatedAt;

	@Column
	private Status status;

	public PaymentResDto toPaymentResDto() {
		return PaymentResDto.builder()
			.payType(payType)
			.amount(amount)
			.orderName(orderName)
			.orderId(orderId)
			.userEmail(user.getEmail())
			.userName(user.getName())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.cancelYN(cancelYN)
			.failReason(failReason)
			.status(status)
			.build();
	}
}
