package com.amzmall.project.exception;

import com.amzmall.project.advice.ExMessage;

public class BusinessException extends RuntimeException {
    public BusinessException(ExMessage exMessage) {
        super(exMessage.getMessage());
    }
    public BusinessException(String message) {
        super(message);
    }
}