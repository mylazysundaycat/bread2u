package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.global.jwt.JwtToken;
import com.daegeon.bread2u.global.jwt.JwtTokenProvider;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    HttpSession session;

    @Transactional
    public JwtToken signIn(String email, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    public MemberDto loginValidation(final Model model, final HttpServletRequest request){
        session = request.getSession(false);
        if(session==null) {
            model.addAttribute("loginmember", null);
            return null;
        }
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        if(loginMember!=null) model.addAttribute("loginmember", loginMember);
        else model.addAttribute("loginmember", null);

        return loginMember;
    }
}
