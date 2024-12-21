package com.daegeon.bread2u.module.member.dto;


import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginMemberRequest {
    private String email;
    public LoginMemberRequest(String email) {
        this.email = email;
    }
}
