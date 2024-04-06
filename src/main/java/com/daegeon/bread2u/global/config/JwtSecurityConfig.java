package com.daegeon.bread2u.global.config;

import com.daegeon.bread2u.global.jwt.JwtAuthFilter;
import com.daegeon.bread2u.global.jwt.JwtUtil;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtUtil jwtUtil;
    public JwtSecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configure(HttpSecurity http) {
        /*JwtAuthFilter에 jwtUtil을 전달하여 UsernamePasswordAuthenticationFilter전에 필터로 등록한다.*/
        http.addFilterBefore(
                new JwtAuthFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}