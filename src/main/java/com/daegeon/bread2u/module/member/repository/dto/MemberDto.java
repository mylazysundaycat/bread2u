package com.daegeon.bread2u.module.member.repository.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private String username;
    private String nickname;
    private String email;
    private String role;
}
