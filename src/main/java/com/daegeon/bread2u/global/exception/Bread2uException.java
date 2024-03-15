package com.daegeon.bread2u.global.exception;

import lombok.Getter;

@Getter
public class Bread2uException extends RuntimeException{
    private final ErrorCode errorCode;
    public Bread2uException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
