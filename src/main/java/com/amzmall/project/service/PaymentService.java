package com.amzmall.project.service;

import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CustomerService customerService;
    private final PaymentRepository paymentRepository;

}
