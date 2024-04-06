package com.daegeon.bread2u.module.member.repository.dto;


import com.daegeon.bread2u.module.member.entity.Member;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;

    public static SignUpRequestDto fromEntity(Member member) {
        return SignUpRequestDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .role("USER")
                .build();
    }
}
