package com.amzmall.project.payment.controller;


import com.amzmall.project.payment.domain.dto.PaymentDto;
import com.amzmall.project.payment.domain.dto.PaymentFailDto;
import com.amzmall.project.payment.domain.dto.PaymentReqDto;
import com.amzmall.project.payment.domain.dto.PaymentResDto;
import com.amzmall.project.payment.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.payment.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.response.ListResult;
import com.amzmall.project.response.ResponseService;
import com.amzmall.project.response.SingleResult;
import com.amzmall.project.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "payments", description="결제")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;

    @PostMapping
    @Operation(summary="결제 요청", description="결제 요청에 필요한 값들을 반환합니다.")
    public SingleResult<PaymentResDto> requestPayment(
            @Parameter(name = "PaymentReqDto", description = "요청 객체", required = true)
            @ModelAttribute PaymentReqDto paymentReqDto) {
       try {
           return responseService.getSingleResult(paymentService.requestPayment(paymentReqDto));
       } catch (Exception e){
           e.printStackTrace();
           throw new BusinessException(e.getMessage());
       }
    }

    @GetMapping("/success")
    @Operation(summary="결제 성공 리다이렉트 URL", description="결제 성공 시 토스 페이먼츠에 최종 결제 승인 요청을 보냅니다.")
    public SingleResult<PaymentResSuccessDto> requestFinalPayment(
            @Parameter(name = "paymentKey", description = "토스페이먼츠 측 결제 고유 번호", required = true)
            @RequestParam("paymentKey") String paymentKey,
            @Parameter(name = "orderId", description = "상점 측 주문 고유 번호", required = true)
            @RequestParam("orderId") String orderId,
            @Parameter(name = "amount", description = "실제 결제 금액", required = true)
            @RequestParam("amount") Long amount,
            @Parameter(name = "paymentType", description = "결제 타입")
            @RequestParam("paymentType") PAYMENT_TYPE paymentType) {
        try {
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
    @Operation(summary="결제 실패 리다이렉트 URL", description="결제 실패 시 에러 내역을 보냅니다.")
    public SingleResult<PaymentFailDto> requestFail (
            @Parameter(name = "errorCode", description = "에러 코드", required = true)
            @RequestParam("errorCode") String errorCode,
            @Parameter(name = "errorMessage", description = "에러 메시지", required = true)
            @RequestParam("errorMessage") String errorMessage,
            @Parameter(name = "orderId", description = "주문 고유 번호", required = true)
            @RequestParam("orderId") String orderId) {
        try {
            return responseService.getSingleResult(
                    paymentService.requestFail(errorCode, errorMessage, orderId)
            );
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary="모든 결제 내역 조회", description="고객의 모든 완료된 결제 내역을 조회합니다.")
    public ListResult<PaymentDto> getAllPayments(
        @Parameter(name = "customerEmail", description = "고객 이메일", required = true)
        @RequestParam("customerEmail") String customerEmail,
        @Parameter(name = "page", description = "페이지 번호 (0부터)", required = true)
        @RequestParam(name = "page",defaultValue = "0") int page,
        @Parameter(name = "size", description = "페이지 사이즈", required = true)
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        try {
            return responseService.getListResult(
                paymentService.getAllPayments(customerEmail, pageRequest)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/one")
    @Operation(summary="결제 한 건 조회", description="주문 고유 번호로 고객의 결제 내역을 조회")
    public SingleResult<PaymentDto> getOnePayment(
        @Parameter(name = "customerEmail", description = "고객 이메일", required = true)
        @RequestParam("customerEmail") String customerEmail,
        @Parameter(name = "orderId", description = "주문 고유 번호", required = true)
        @RequestParam("orderId") String orderId
    ) {
        try {
            return responseService.getSingleResult(
                paymentService.getOnePayment(customerEmail, orderId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

//    @PostMapping("/cancel")
//    @Operation(summary="결제 취소", description = "완료된 결제의 결제 취소를 요청합니다.")
//    public SingleResult<String> requestCancelPayment(
//            @Parameter(name = "paymentKey", description = "토스페이먼츠 측 결제 고유 번호", required = true)
//            @RequestParam("paymentKey") String paymentKey,
//            @Parameter(name = "cancelReason", description = "결제 취소 이유", required = true)
//            @RequestParam("cancelReason") String cancelReason) {
//        try{
//            return responseService.getSingleResult(paymentService.requestCancelPayment(paymentKey, cancelReason));
//        } catch (Exception e){
//            e.printStackTrace();
//            throw new BusinessException(e.getMessage());
//        }
//    }

}