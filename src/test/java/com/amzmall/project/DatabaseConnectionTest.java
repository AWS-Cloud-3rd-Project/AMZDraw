package com.amzmall.project;

import com.amzmall.project.domain.entity.PayType;
import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.repository.PaymentRepository;
import com.amzmall.project.service.PaymentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class DatabaseConnectionTest {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentService paymentService;

	@Test
	public void testSavePayment() {
		// Given
		Payment payment = Payment.builder()
				.payType(PayType.CARD)
				.amount(23000L)
				.orderName("김태준")
				.orderId(UUID.randomUUID().toString())
				.customerEmail("test@example.com")
				.customerName("Test Customer")
				.paySuccessYn(false)
				.build();

		// When
		Payment savedPayment = paymentRepository.save(payment);

		// Then
		assertThat(savedPayment.getPaymentId()).isNotNull();
	}

}