package com.amzmall.project.controller;


import com.amzmall.project.domain.dto.PaymentReqDto;
import com.amzmall.project.domain.dto.PaymentResDto;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.response.ResponseService;
import com.amzmall.project.response.SingleResult;
import com.amzmall.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;
    private final ResponseService responseService;
    @PostMapping("/payment")
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
}