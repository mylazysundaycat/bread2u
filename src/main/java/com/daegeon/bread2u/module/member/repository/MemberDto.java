package com.daegeon.bread2u.module.member.repository;


import com.daegeon.bread2u.module.member.entity.Member;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private String membername;
    private String nickname;
    private String email;
    private String password;
    private String role;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .membername(member.getMembername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .role("USER")
                .build();
    }
    public static MemberDto from(LoginRequestDto loginRequestDto) {
        return MemberDto.builder()
                .membername(loginRequestDto.getMembername())
                .nickname(loginRequestDto.getNickname())
                .email(loginRequestDto.getEmail())
                .role("USER")
                .build();
    }
}
