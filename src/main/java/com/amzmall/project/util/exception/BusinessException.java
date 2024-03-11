package com.amzmall.project.util.exception;

import com.amzmall.project.util.advice.ExMessage;

public class BusinessException extends RuntimeException {
    public BusinessException(ExMessage exMessage) {
        super(exMessage.getMessage());
    }
    public BusinessException(String message) {
        super(message);
    }
}