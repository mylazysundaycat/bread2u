package com.daegeon.bread2u.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        //토큰이 있을 떄
        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                log.warn("JWT Token 인증 실패");
                throw new IllegalArgumentException("JWT Token 인증 실패");
            }
            setAuthentication(token);
        }
        //토큰이 있는 경우

        /*다음 필터 진행*/
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String token) {
        /*jwt 인증 성공 시*/
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.getAuthentication(token);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }




}