package com.amzmall.project.exception;

public class NotFoundCartException extends RuntimeException{
    public NotFoundCartException(String message) {
        super(message);
    }
}