package com.daegeon.bread2u.global.token.repository.dto;

import lombok.Builder;

@Builder
public class LoginResponseDto {
    private Long memberId;
    private String token;
}
