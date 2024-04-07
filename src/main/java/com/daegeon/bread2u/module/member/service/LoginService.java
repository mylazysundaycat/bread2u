package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.global.exception.Bread2uException;
import com.daegeon.bread2u.global.exception.ErrorCode;
import com.daegeon.bread2u.global.jwt.JwtUtil;
import com.daegeon.bread2u.global.redis.RedisService;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Role;
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
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final RedisService redisService;
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

        String jwtToken = jwtUtil.createToken(member.getUsername(), member.getRole());
        redisService.setValues(member.getUsername(), jwtToken); // key값이 username

        response.addHeader(AUTHORIZATION_HEADER, jwtToken);
    }

    // Redis 이용하여 Token 저장
    public void setAuthentication(String username, Role role){
        String jwtToken = jwtUtil.createToken(username, role);
        redisService.setValues(username, jwtToken); // key값이 username
    }

    // Redis로 반환받은 Token을 HttpServletResponse Header에 저장
    public String getAuthentication(String username, HttpServletResponse response){
        String jwtToken = redisService.getValues(username);
        response.addHeader(AUTHORIZATION_HEADER, jwtToken);
        return jwtToken;
    }

}
