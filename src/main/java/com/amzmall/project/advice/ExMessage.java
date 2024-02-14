package com.amzmall.project.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExMessage {

    DB_ERROR_SAVE("객체 저장에 실패했습니다."),
    RESPONSE_NULL("응답값이 비어있습니다.")
    ;

    private final String message;
}