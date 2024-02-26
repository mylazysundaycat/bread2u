package com.daegeon.bread2u.domain.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String email;
    private String password;
    private String nickname;
}
