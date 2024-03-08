package com.daegeon.bread2u.global.token.repository.dto;

import com.daegeon.bread2u.module.member.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .membername(membername)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(membername, password);
    }
}
