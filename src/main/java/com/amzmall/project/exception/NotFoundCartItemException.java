package com.amzmall.project.exception;

public class NotFoundCartItemException extends RuntimeException{
    public NotFoundCartItemException(String message) {
        super(message);
    }
}
