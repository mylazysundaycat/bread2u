package com.daegeon.bread2u.global.config;

import com.daegeon.bread2u.global.auth.AuthenticationArgumentResolver;
import com.daegeon.bread2u.global.interceptor.AuthenticationInterceptor;
import com.daegeon.bread2u.global.jwt.JwtExtractor;
import com.daegeon.bread2u.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(jwtProvider, jwtExtractor))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/kakao/*")
                .excludePathPatterns("/api/bread/*")
                .excludePathPatterns("/api/bread")
                .excludePathPatterns("/api/scrap/**")
                .excludePathPatterns("/api/member")
                .excludePathPatterns("/kakao/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationArgumentResolver(jwtProvider));
    }
}
