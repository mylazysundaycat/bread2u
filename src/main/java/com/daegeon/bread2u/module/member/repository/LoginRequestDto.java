package com.daegeon.bread2u.module.member.repository;

import com.daegeon.bread2u.module.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String membername;
    private String nickname;
    private String email;
    private String password;
    private String role = "USER";

    public static LoginRequestDto from(Member member) {
        return LoginRequestDto.builder()
                .membername(member.getMembername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .role("USER")
                .build();
    }
}