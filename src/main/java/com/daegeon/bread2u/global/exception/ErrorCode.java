package com.daegeon.bread2u.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400

    // 401
    MISMATCHED_EMAIL_OR_PASSWORD(UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다."),
    // 404
    NOT_FOUND_MEMBER(NOT_FOUND, "존재하지 않는 회원입니다."),
    // 500
    DUPLICATE_EMAIL(INTERNAL_SERVER_ERROR, "이미 가입된 이메일 주소입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
