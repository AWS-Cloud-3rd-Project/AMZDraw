package com.amzmall.project.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExMessage {
    DB_ERROR_SAVE("객체 저장에 실패했습니다."),
    PAYMENT_ERROR_ORDER_NAME("주문하신 상품 이름이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PRICE("주문하신 상품 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PAY_TYPE("결제수단 선택이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_AMOUNT("결제 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER("결제 관련 오류가 발생했습니다."),
    PAYMENT_ERROR_ORDER_NOTFOUND("해당 결제 내역을 조회할 수 없습니다."),
    PAYMENT_ERROR_NOT_PAY("고객이 아직 결제를 완료하지 않았습니다."),
    PAYMENT_CANCEL_ERROR_NOT_MATCH_AMOUNT("취소 금액과 결제 금액이 다릅니다."),
    PAYMENT_CANCEL_ERROR_FAIL("알 수 없는 이유로 결제 취소에 실패했습니다.")
    ;

    private final String message;
}
