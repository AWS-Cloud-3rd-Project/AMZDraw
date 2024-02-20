package com.amzmall.project.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExMessage {

    DB_ERROR_SAVE("객체 저장에 실패했습니다."),
    CUSTOMER_ERROR_NOT_FOUND("해당 고객은 존재하지 않습니다"),
    PAYMENT_ERROR_ORDER_NAME("주문하신 상품 이름이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PRICE("주문하신 상품 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PAYMENT_TYPE("결제 수단 선택이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_AMOUNT("결제 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER("결제 관련 오류가 발생했습니다."),
    PAYMENT_ERROR_ORDER_NOT_FOUND("해당 주문 내역을 조회할 수 없습니다."),
    PAYMENT_ERROR_NOT_PAY("아직 결제되지 않은 건입니다."),
    PAYMENT_CANCEL_ERROR_NOT_MATCH_AMOUNT("취소 금액과 결제 금액이 다릅니다."),
    PAYMENT_CANCEL_ERROR_FAIL("알 수 없는 이유로 결제 취소에 실패했습니다."),
    NOT_YET_DEFINED_ERROR("아직 정의되지 않은 에러"),
    RESPONSE_NULL("응답값이 비어있습니다.")
    ;

    private final String message;
}
