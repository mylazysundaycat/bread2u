package com.daegeon.bread2u.global.token.service;

import com.daegeon.bread2u.global.jwt.JwtFilter;
import com.daegeon.bread2u.global.jwt.TokenProvider;
import com.daegeon.bread2u.global.token.entity.RefreshToken;
import com.daegeon.bread2u.global.token.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.global.token.repository.dto.LoginResponseDto;
import com.daegeon.bread2u.module.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

//    @Transactional
//    public TokenDto login(MemberDto memberDto) {
//        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken = memberDto.toAuthentication();
//
//        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
//        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        String tokenDto = tokenProvider.generateTokenDto(authentication);
//        String jwt = tokenProvider.generateTokenDto(authentication).getAccessToken();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        // 4. RefreshToken 저장
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authentication.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//        refreshTokenRepository.save(refreshToken);
//
//        // 5. 토큰 발급
//        return tokenDto;
//    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        //1. login ID/PW 기반으로 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        System.out.println("login에서 토큰 만들어지는지 테스트1: "+authenticationToken);
        //2. 검증(사용자 비밀번호 체크)이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //3. 인증정보 기반으로 JWT token 생성
        String accessToken = tokenProvider.generateToken(authentication);
        Member member = (Member) authentication.getPrincipal(); // user 정보


        return LoginResponseDto.builder()
                .token("Bearer "+accessToken)
                .memberId(member.getId())
                .build();
    }
}
