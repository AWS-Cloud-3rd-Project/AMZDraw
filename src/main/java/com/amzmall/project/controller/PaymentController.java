package com.amzmall.project.controller;


import com.amzmall.project.domain.dto.PaymentReqDto;
import com.amzmall.project.domain.dto.PaymentResDto;
import com.amzmall.project.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.response.ResponseService;
import com.amzmall.project.response.SingleResult;
import com.amzmall.project.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;

    @PostMapping
    @Operation(summary="결제 요청", description="결제 요청에 필요한 값들을 반환합니다.")
    public SingleResult<PaymentResDto> requestPayments(
            @Parameter(name = "PaymentReqDto", description = "요청 객체", required = true)
            @ModelAttribute PaymentReqDto paymentReqDto
    ) {
       try {
           return responseService.getSingleResult(paymentService.requestPayments(paymentReqDto));
       } catch (Exception e){
           e.printStackTrace();
           throw new BusinessException(e.getMessage());
       }
    }

    @GetMapping("/success")
    @Operation(summary="결제 성공 리다이렉트", description="결제 성공 시 최종 결제 승인 요청을 보냅니다.")
    public SingleResult<PaymentResSuccessDto> requestFinalPayments(
            @Parameter(name = "paymentKey", description = "토스 측 결제 고유 번호", required = true)
            @RequestParam("paymentKey") String paymentKey,
            @Parameter(name = "orderId", description = "우리 측 주문 고유 번호", required = true)
            @RequestParam("orderId") String orderId,
            @Parameter(name = "amount", description = "실제 결제 금액", required = true)
            @RequestParam("amount") Long amount,
            @Parameter(name = "paymentType", description = "결제 타입")
            @RequestParam("paymentType") PAYMENT_TYPE paymentType)
    {
        try{
            System.out.println("paymentKey = " + paymentKey);
            System.out.println("orderId = " + orderId);
            System.out.println("amount = " + amount);
            System.out.println("paymentType" + paymentType);
            paymentService.verifyRequest(paymentKey, orderId, amount);
            PaymentResSuccessDto result = paymentService.requestFinalPayment(paymentKey, orderId, amount);

            return responseService.getSingleResult(result);
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
    @GetMapping("/fail")
    public String r (){
        return("fail.html");
    }
}