package com.daegeon.bread2u.global.interceptor;

import com.daegeon.bread2u.global.jwt.JwtExtractor;
import com.daegeon.bread2u.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;



public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;

    public AuthenticationInterceptor(JwtProvider jwtProvider, JwtExtractor jwtExtractor) {
        this.jwtProvider = jwtProvider;
        this.jwtExtractor = jwtExtractor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String token = jwtExtractor.extractToken(request);
        if (jwtProvider.validateAvailableToken(token)) return true;
        return false;
    }
}
