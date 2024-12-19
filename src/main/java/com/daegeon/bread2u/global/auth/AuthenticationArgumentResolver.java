package com.daegeon.bread2u.global.auth;

import com.daegeon.bread2u.global.jwt.JwtExtractor;
import com.daegeon.bread2u.global.jwt.JwtProvider;
import com.daegeon.bread2u.module.member.dto.LoginMemberRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;


@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtProvider jwtProvider;
    public AuthenticationArgumentResolver(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final String token = JwtExtractor.extractToken(Objects.requireNonNull(request));
        final String email = jwtProvider.getSubject(token);
        return new LoginMemberRequest(email);
    }
}
