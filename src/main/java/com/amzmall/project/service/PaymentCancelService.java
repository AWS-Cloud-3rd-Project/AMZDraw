package com.amzmall.project.service;

import com.amzmall.project.advice.ExMessage;
import com.amzmall.project.config.TossPaymentConfig;
import com.amzmall.project.domain.dto.CancelPaymentResDto;
import com.amzmall.project.domain.dto.PaymentResSuccessDto;
import com.amzmall.project.domain.entity.CancelPayment;
import com.amzmall.project.domain.entity.PAYMENT_TYPE;
import com.amzmall.project.domain.entity.Payment;
import com.amzmall.project.exception.BusinessException;
import com.amzmall.project.repository.CancelPaymentRepository;
import com.amzmall.project.repository.PaymentRepository;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCancelService {
	private final PaymentRepository paymentRepository;
	private final TossPaymentConfig tossPaymentConfig;
	private final CancelPaymentRepository cancelPaymentRepository;
	@Transactional
	public void requestCancelPayment(String paymentKey, String cancelReason) {
		URI cancelUri = URI.create(tossPaymentConfig.getBasicUrl() + paymentKey + "/cancel");
		String testSecretKey = tossPaymentConfig.getTestSecretKey() + ":"; // 시크릿 키는 ":" 을 붙여야 함

		byte[] encodedKey = Base64.getEncoder()
			.encode(testSecretKey
				.getBytes(StandardCharsets.UTF_8));

		String encodedAuth = new String(encodedKey);
		HttpHeaders httpHeaders = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		httpHeaders.setBasicAuth(encodedAuth);  // Base64로 인코딩한 문자열을 HTTP 요청 헤더에 추가하여 인증
		httpHeaders.setContentType(MediaType.APPLICATION_JSON); // 콘텐츠 유형을 JSON 형식으로 명시
		httpHeaders.setAccept(Collections.singletonList(
			MediaType.APPLICATION_JSON));   // 서버로부터 JSON 형식의 데이터를 받는 것을 명시

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cancelReason", cancelReason);

		PaymentResSuccessDto paymentResCancelDto;

		try {
			System.out.println("cancel uri  : " + cancelUri);
			paymentResCancelDto = restTemplate.postForObject(
				cancelUri,
				new HttpEntity<>(jsonObject, httpHeaders),
				PaymentResSuccessDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage().split(":")[1]);
		}

		if (paymentResCancelDto == null) throw new BusinessException(ExMessage.RESPONSE_NULL);

		Payment payment = paymentRepository.findByPaymentKey(paymentKey)
			.orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND));

		Long cancelAmount = paymentResCancelDto.getCancels()[0].getCancelAmount();
		try {
			cancelPaymentSave(
				payment.getPaymentType(), paymentKey, paymentResCancelDto, cancelAmount);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	private void cancelPaymentSave( PAYMENT_TYPE paymentType, String paymentKey, PaymentResSuccessDto paymentCancelResDto, Long cancelAmount) {
		paymentRepository
			.findByPaymentKey(paymentKey)						// 결제 정보를 찾기
			.filter(P -> P.getAmount().equals(cancelAmount))	// 결제 금액이 취소 금액과 일치하는지 확인
			.ifPresentOrElse(P -> {
				log.info("[결제 취소 고객 이력에 추가]");
				CancelPayment cancelPayment;
				cancelPayment = paymentCancelResDto.toCancelPayment();
				P.getCustomer().addCancelPayment(cancelPayment);
				log.info("[결제 취소 준비]");
				P.getCustomer().getPayments()
					.stream()
					.filter(p -> p.getPaymentKey().equals(paymentKey))
					.findFirst()
					.orElseThrow(() -> new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND))
					.setCancelYn("Y");
				log.info("[결제 취소 완료]");
			}, () -> {
				throw new BusinessException(ExMessage.PAYMENT_ERROR_ORDER_NOT_FOUND);
			});
	}

	@Transactional(readOnly = true)
	public List<CancelPaymentResDto> getAllCancelPayments(String customerEmail, PageRequest pageRequest) {
		return cancelPaymentRepository
			.findAllByCustomerEmail(customerEmail, pageRequest)
			.stream()
			.map(CancelPayment::toCancelPaymentResDto)
			.collect(Collectors.toList());
	}
}
