package com.daegeon.bread2u.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),

    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다. 다시 요청해주세요.");



    private final HttpStatus httpStatus;
    private final String message;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public String getCode() {
        return this.name();
    }
    public String getMessage() {
        return message;
    }
}
