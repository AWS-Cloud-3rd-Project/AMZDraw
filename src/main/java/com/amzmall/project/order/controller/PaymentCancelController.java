package com.amzmall.project.order.controller;

import com.amzmall.project.order.domain.dto.CancelPaymentResDto;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.dto.CommonResult;
import com.amzmall.project.util.dto.ListResult;
import com.amzmall.project.util.service.ResponseService;
import com.amzmall.project.order.service.PaymentCancelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "cancels", description="결제 취소")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cancel")
public class PaymentCancelController {

	private final ResponseService responseService;
	private final PaymentCancelService paymentCancelService;

	@PostMapping
	@Operation(summary="결제 취소", description = "완료된 결제의 결제 취소를 요청합니다.")
	public CommonResult requestCancelPayment(
		@Parameter(name = "paymentKey", description = "토스페이먼츠 측 결제 고유 번호", required = true)
		@RequestParam("paymentKey") String paymentKey,
		@Parameter(name = "cancelReason", description = "결제 취소 이유", required = true)
		@RequestParam("cancelReason") String cancelReason) {
		try{
			paymentCancelService.requestCancelPayment(paymentKey, cancelReason);
			return responseService.getSuccessResult();
		} catch (Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	@GetMapping
	@Operation(summary="결제 취소 내역 전체 조회", description = "고객이 취소한 모든 결제 내역을 조회합니다.")
	public ListResult<CancelPaymentResDto> getAllCancelPayment(
		@Parameter(name = "customerEmail", description = "고객 이메일", required = true)
		@RequestParam("customerEmail") String customerEmail,
		@Parameter(name = "page", description = "페이지 번호 (0부터)", required = true)
		@RequestParam(name = "page", defaultValue = "0") int page,
		@Parameter(name = "size", description = "페이지 사이즈", required = true)
		@RequestParam(name = "size",defaultValue = "10") int size
	) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("cancelDate").descending());
		try {
			return responseService.getListResult(
				paymentCancelService.getAllCancelPayments(customerEmail, pageRequest)
			);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

}
