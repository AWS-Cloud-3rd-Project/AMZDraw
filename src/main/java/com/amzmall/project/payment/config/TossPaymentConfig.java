package com.amzmall.project.payment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {
    @Value("${toss.payments.client_key}")
    private String testClientKey;

    @Value("${toss.payments.secret_key}")
    private String testSecretKey;

    @Value("${toss.payments.success_url}")
    private String successUrl;

    @Value("${toss.payments.fail_url}")
    private String failUrl;

    // 토스페이먼츠 결제 승인 요청 URL
    @Value("${toss.payments.basic_url}")
    public String basicUrl;

}
