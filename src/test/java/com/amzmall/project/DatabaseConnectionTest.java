package com.amzmall.project;

import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.domain.entity.PayType;
import com.amzmall.project.domain.entity.Status;
import com.amzmall.project.repository.JpaPaymentRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.persistence.EntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DatabaseConnectionTest {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private JpaPaymentRepository jpaPaymentRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void testDatabaseConnection() {
		assertThat(entityManager).isNotNull();
		jdbcTemplate.execute("SELECT 1");
	}

	@Test
	@Rollback(false) // 롤백 방지 (DDL 실행을 위해)
	public void testPaymentTable() {
		// Given: Payment 엔터티 생성
		Payment payment = Payment.builder()
			.payType(PayType.CARD)
			.amount(10000L)
			.orderName("Sample Order")
			.orderId("123456")
			.paySuccessYN(true)
			.user(null) // 사용자 엔티티
			.paymentKey("payment123")
			.failReason(null)
			.cancelYN(false)
			.cancelReason(null)
			.createdAt(Timestamp.valueOf(LocalDateTime.now()))
			.updatedAt(Timestamp.valueOf(LocalDateTime.now()))
			.status(Status.A)
			.build();

		// When: Payment 저장
		Payment savedPayment = jpaPaymentRepository.save(payment);

		// Then: 조회 결과 검증
		assertThat(savedPayment).isNotNull();
		assertThat(savedPayment.getPayType()).isEqualTo(PayType.CARD);
		assertThat(savedPayment.getAmount()).isEqualTo(10000L);

		// 테스트에 사용한 데이터 삭제 (옵션)
		jpaPaymentRepository.delete(savedPayment);
	}


}