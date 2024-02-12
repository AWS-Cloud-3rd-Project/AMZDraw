package com.amzmall.project.controller;

import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.response.CommonResult;
import com.amzmall.project.response.ResponseService;
import com.amzmall.project.service.PaymentCancelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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


}
