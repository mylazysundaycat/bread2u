package com.daegeon.bread2u.global.exception;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException{
    private final ErrorCode errorCode;
    public TokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
