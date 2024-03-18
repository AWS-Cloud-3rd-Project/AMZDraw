package com.amzmall.project.util.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExMessage {
    ERROR_REQUEST_FORM("요청 형식이 잘못되었습니다."),
    DB_ERROR_SAVE("객체 저장에 실패했습니다."),
    USER_ERROR_NOT_FOUND("해당 고객은 존재하지 않습니다"),
    USER_ERROR_DUPLICATE("해당 이메일이 이미 가입되어 있습니다."),
    PRODUCT_ERROR_NOT_EXIST("해당 상품은 존재하지 않습니다."),
    PRODUCT_ERROR_NO_STOCK("상품의 재고가 부족합니다."),
    PRODUCT_ERROR_UPLOAD_LIMIT("최대 3개의 사진을 업로드할 수 있습니다."),
    PRODUCT_ERROR_UPLOAD_IMAGE("상품 이미지 업로드에 실패하였습니다."),
    PRODUCT_ERROR_NO_MODIFY("수정할 내용이 없습니다."),
    PRODUCT_ERROR_CATEGORY("해당 카테고리를 찾을 수 없습니다."),
    PAYMENT_ERROR_ORDER_NAME("주문하신 상품 이름이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PRICE("주문하신 상품 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_PAYMENT_TYPE("결제 수단 선택이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER_AMOUNT("결제 금액이 잘못되었습니다."),
    PAYMENT_ERROR_ORDER("결제 관련 오류가 발생했습니다."),
    PAYMENT_ERROR_ORDER_NOT_FOUND("해당 주문 내역을 조회할 수 없습니다."),
    PAYMENT_ERROR_NOT_PAY("아직 결제되지 않은 건입니다."),
    PAYMENT_ERROR_ALREADY_APPLY("이미 결제 신청된 건입니다. 결제를 완료해주세요."),
    PAYMENT_CANCEL_ERROR_NOT_MATCH_AMOUNT("취소 금액과 결제 금액이 다릅니다."),
    PAYMENT_CANCEL_ERROR_FAIL("알 수 없는 이유로 결제 취소에 실패했습니다."),
    NOT_YET_DEFINED_ERROR("아직 정의되지 않은 에러"),
    RESPONSE_NULL("응답값이 비어있습니다."),
    QUESTION_ERROR_NOT_FOUND("해당 질문을 찾을 수 없습니다."),
    QUESTION_ERROR_NOT_AVAILABLE("해당 질문은 비활성화 되어있습니다."),
    REPLY_ERROR_ALREADY_REPLIED("이미 답변 완료된 질문입니다."),
    REPLY_ERROR_NOT_FOUND("해당 답글을 찾을 수 없습니다."),
    REPLY_ERROR_FORM("답변 입력 형식이 잘못되었습니다."),
    REPLY_ERROR_NOT_AVAILABLE_FOR_UPDATE("답변이 수정할 수 없는 상태입니다."),
    REPLY_ERROR_ALREADY_DEACTIVATED("비활성화 된 답변입니다. 새로 답변을 등록해주세요.")
    ;
    private final String message;
}
