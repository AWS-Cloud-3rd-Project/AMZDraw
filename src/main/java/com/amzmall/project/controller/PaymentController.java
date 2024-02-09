package com.amzmall.project.controller;


import com.amzmall.project.domain.dto.PaymentReqDto;
import com.amzmall.project.domain.dto.PaymentResDto;
import com.amzmall.project.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.response.ResponseService;
import com.amzmall.project.response.SingleResult;
import com.amzmall.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;

    @PostMapping
    public SingleResult<PaymentResDto> requestPayments(
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
    public SingleResult<PaymentResSuccessDto> requestFinalPayments(@RequestParam("paymentKey") String paymentKey,
                                                     @RequestParam("orderId") String orderId,
                                                     @RequestParam("amount") Long amount){
        try{
            System.out.println("paymentKey = " + paymentKey);
            System.out.println("orderId = " + orderId);
            System.out.println("amount = " + amount);
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