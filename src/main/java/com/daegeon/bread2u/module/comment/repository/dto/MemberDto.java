package com.daegeon.bread2u.module.comment.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String email;
    private String password;
    private String nickname;
}
