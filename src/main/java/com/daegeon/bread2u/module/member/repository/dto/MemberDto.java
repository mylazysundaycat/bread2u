package com.daegeon.bread2u.module.member.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String memberName;
    private String nickname;
    private String email;
    private String password;
    private String role = "USER";
}
