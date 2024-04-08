package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.global.exception.Bread2uException;
import com.daegeon.bread2u.global.exception.ErrorCode;
import com.daegeon.bread2u.global.jwt.JwtUtil;
import com.daegeon.bread2u.global.redis.RedisService;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Role;
import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LoginService {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    /*로그인*/
    @Transactional
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(()->new Bread2uException(ErrorCode.MISSING_CREDENTIALS));

        /*비밀번호 다름.*/
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new Bread2uException(ErrorCode.MISMATCHED_EMAIL_OR_PASSWORD);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
        );

        String accessToken = jwtUtil.createAccessToken(authentication, member.getId());
        String refreshToken = jwtUtil.createRefreshToken(authentication, member.getId());

        Cookie cookie1 = createAccessCookie(accessToken);
        Cookie cookie2 = jwtUtil.createCookie(refreshToken);

        response.addCookie(cookie1);
        response.addCookie(cookie2);

        return accessToken;
    }

    public Cookie createAccessCookie(String accessToken) {
        /*토큰을 쿠키로 발급 및 응답에 추가*/
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, accessToken);

        cookie.setMaxAge(3 * 60 * 60); // 3시간동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);

        return cookie;
    }

    public void addLoginMember(UserDetailsImpl userDetails, Model model){
        if(userDetails.getMember()!=null)
            model.addAttribute("loginMember",userDetails.getMember());
    }

    public void getMember(HttpServletRequest request,
                          Model model){
        Member loginMember = null;
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue();// 쿠키 값 가져오기
                if (name.equals("Authorization")) {
                    token = value;
                }
            }
        }
        if (StringUtils.hasText(token) && token.startsWith("Bearer=")) {
            token = token.substring(7);
            Claims claim = jwtUtil.getUserInfoFromToken(token);
            Long memberId = claim.get("memberId", Long.class);
            loginMember = memberRepository.findById(memberId).orElseThrow();
            model.addAttribute("loginMember",loginMember);
        }
    }

//    // Redis 이용하여 Token 저장
//    public void setAuthentication(String username, Role role){
//        String jwtToken = jwtUtil.createAccessToken(username, role);
//        redisService.setValues(username, jwtToken); // key값이 username
//    }
//
//    // Redis로 반환받은 Token을 HttpServletResponse Header에 저장
//    public String getAuthentication(String username, HttpServletResponse response){
//        String jwtToken = redisService.getValues(username);
//        response.addHeader(AUTHORIZATION_HEADER, jwtToken);
//        return jwtToken;
//    }

}
