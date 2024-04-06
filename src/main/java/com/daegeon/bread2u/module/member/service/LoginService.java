package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.global.exception.Bread2uException;
import com.daegeon.bread2u.global.exception.ErrorCode;
import com.daegeon.bread2u.global.jwt.JwtUtil;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    /*로그인*/
    @Transactional
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(()->new Bread2uException(ErrorCode.MISSING_CREDENTIALS));

        /*비밀번호 다름.*/
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new Bread2uException(ErrorCode.MISMATCHED_EMAIL_OR_PASSWORD);
        }

        /*토큰을 쿠키로 발급 및 응답에 추가*/
        //TODO 추후에 REDIS로 교체
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(member.getUsername(), member.getRole()));
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);

        response.addCookie(cookie);

    }


}
