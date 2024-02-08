package com.amzmall.project.service;

import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PaymentService {
    private final CustomerService customerService;
    private final PaymentRepository paymentRepository;
    @Autowired
    public PaymentService(CustomerService customerService, PaymentRepository paymentRepository) {
        this.customerService = customerService;
        this.paymentRepository = paymentRepository;
    }

//    @Value("${toss.payments.client_key}")
//    private String testClientKey;
//
//    @Value("${toss.payments.secret_key}")
//    private String testSecretKey;
//
//    @Value("${toss.payments.success_url}")
//    private String successCallBackUrl;
//
//    @Value("${toss.payments.fail_url}")
//    private String failCallBackUrl;

//    public Payment requestPayment(Payment payment, String customerEmail) {
//        Customer customer = customerService.findCustomer(customerEmail);
//        payment.setCustomer(customer);
//        return paymentRepository.save(payment);
//    }

    @Transactional
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
