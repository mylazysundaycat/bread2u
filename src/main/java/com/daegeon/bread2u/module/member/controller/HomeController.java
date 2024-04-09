package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.global.jwt.JwtUtil;
import com.daegeon.bread2u.global.redis.RedisService;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.service.LoginService;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Operation(summary = "홈 화면", description = "홈 화면으로 이동한다")
    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       HttpServletRequest request,
                       Model model) {
//
//        Cookie[] cookies = request.getCookies();
//        String token = "";
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                String name = c.getName(); // 쿠키 이름 가져오기
//                String value = c.getValue();// 쿠키 값 가져오기
//                if (name.equals("Authorization")) {
//                    token = value;
//                }
//            }
//        }
//        if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
//            token = token.substring(7);
//
//            Claims claim = jwtUtil.getUserInfoFromToken(token);
//            Long memberId = (Long)claim.get("memberId");
//            Member member = memberService.findById(memberId).orElseThrow();
//
//            new UsernamePasswordAuthenticationToken(member.getUsername()
//                    , member.getPassword());
//
//
//            model.addAttribute("loginMember",member);
//        }

        return "/index";
    }
}
