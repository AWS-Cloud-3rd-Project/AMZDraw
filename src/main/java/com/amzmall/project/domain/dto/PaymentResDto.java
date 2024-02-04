package com.amzmall.project.domain.dto;
import com.amzmall.project.domain.entity.PayType;
import com.amzmall.project.domain.entity.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {
	private PayType payType;		// 지불 방법
	private Long amount;		// 지불 금액
	private String orderName;	// 주문 상품 이름
	private String orderId;		// 주문 고유 ID
	private String userEmail;	// 구매자 이메일
	private String userName;	// 구매자 이름
	private String successUrl;		// 성공 시 콜백

	private String failUrl;			// 실패 시 콜백 주소
	private boolean cancelYN; 		// 취소 여부
	private String cancelReason; 	// 취소 사유
	private LocalDateTime createdAt;		// 결제 날짜
	private LocalDateTime updatedAt;		// 결제 수정 날짜
	private String failReason;		// 실패 사유
	private String paySuccessYn;	// 결제 성공 여부
	private Status status;
}
