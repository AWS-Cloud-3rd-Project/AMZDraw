package com.amzmall.project.order.controller;


import com.amzmall.project.order.domain.dto.PaymentDto;
import com.amzmall.project.order.domain.dto.PaymentFailDto;
import com.amzmall.project.order.domain.dto.OrderReqDto;
import com.amzmall.project.order.domain.dto.OrderResDto;
import com.amzmall.project.order.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.order.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.dto.ListResult;
import com.amzmall.project.util.service.ResponseService;
import com.amzmall.project.util.dto.SingleResult;
import com.amzmall.project.order.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "orders", description="주문")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;

    @PostMapping
    @CrossOrigin(origins = "*") // 모든 도메인에서의 요청 허용
    @Operation(summary="주문 요청", description="주문 요청에 필요한 값들을 반환합니다.")
    public SingleResult<OrderResDto> placeOrder(
        @Parameter(name = "OrderReqDto", description = "요청 객체", required = true)
        @RequestBody OrderReqDto orderReqDto) {
        try {
            return responseService.getSingleResult(paymentService.requestOrder(orderReqDto));
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
            @RequestParam("amount") int amount) {
        try {
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
        @RequestParam(name = "size", defaultValue = "10") int size) {
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
        @RequestParam("orderId") String orderId) {
        try {
            return responseService.getSingleResult(
                paymentService.getOnePayment(customerEmail, orderId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

}