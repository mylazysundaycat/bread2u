package com.daegeon.bread2u.module.member.repository.dto;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private Long id;
    private String username;
    private String password;
    private String role;

    public static MemberDto toDto(LoginRequestDto loginRequestDto) {
        return MemberDto.builder()
                .username(loginRequestDto.getUsername())
                .role(loginRequestDto.getRole())
                .build();
    }
}
