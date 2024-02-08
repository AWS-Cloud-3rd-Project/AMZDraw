//package com.amzmall.project.controller;
//
//
//import com.amzmall.project.config.TossPaymentConfig;
//import com.amzmall.project.domain.dto.PaymentReqDto;
//import com.amzmall.project.domain.dto.PaymentResDto;
//import com.amzmall.project.domain.entity.Customer;
//import com.amzmall.project.service.PaymentService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/payments")
//public class PaymentController {
//    private final PaymentService paymentService;
//    private final TossPaymentConfig tossPaymentConfig;
//
//    public PaymentController(PaymentService paymentService, TossPaymentConfig tossPaymentConfig) {
//        this.paymentService = paymentService;
//        this.tossPaymentConfig = tossPaymentConfig;
//    }
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index(HttpServletRequest request, Model model) throws Exception {
//        return "/index";
//    }
//    @PostMapping("/toss")
//    public ResponseEntity requestTossPayment(@RequestBody PaymentReqDto paymentReqDto) {
//        // PaymentReqDto에서 고객 정보 추출
//        String customerName = paymentReqDto.getCustomerName();
//        PaymentResDto paymentResDto = paymentService.requestPayment(paymentReqDto.toEntity(), customerName).toPaymentDto();
//
//        // Success URL 및 Fail URL 설정
//        paymentResDto.setSuccessUrl(paymentReqDto.getSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : paymentReqDto.getSuccessUrl());
//        paymentResDto.setFailUrl(paymentReqDto.getFailUrl() == null ? tossPaymentConfig.getFailUrl() : paymentReqDto.getFailUrl());
//
//        // ResponseEntity로 응답 반환
//        return ResponseEntity.ok().body(paymentResDto);
//    }
//
//    @RequestMapping(value = "/toss/success", method = RequestMethod.GET)
//    public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
//        return "/success";
//    }
//
//    @RequestMapping(value = "/toss/fail", method = RequestMethod.GET)
//    public String failPayment(HttpServletRequest request, Model model) throws Exception {
//        String failCode = request.getParameter("code");
//        String failMessage = request.getParameter("message");
//
//        model.addAttribute("code", failCode);
//        model.addAttribute("message", failMessage);
//
//        return "/fail";
//    }
//}
