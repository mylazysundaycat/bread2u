package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.global.jwt.JwtToken;
import com.daegeon.bread2u.global.redis.RedisService;
import com.daegeon.bread2u.module.member.repository.LoginRequest;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.service.LoginService;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Login", description = "Login API")
@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {
    private final MemberService memberService;
    private final RedisService redisService;
    private final LoginService loginService;


    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        JwtToken jwtToken = loginService.signIn(email, password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }



    @Operation(summary = "홈 화면", description = "홈 화면으로 이동한다")
    @GetMapping("/")
    public String home(Model model) {
        return "/index";
    }

    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping("/signup")
    public String createMember(Model model) {
        model.addAttribute("member", new LoginRequest());
        return "/member/createMemberForm";
    }

    @Operation(summary = "회원가입", description = "회원가입을 정상적으로 마치고, 로그인 화면으로 이동한다.")
    @PostMapping("/signup")
    public String join(@ModelAttribute LoginRequest memberDto) {
        Member member = Member.from(memberDto);
        memberService.createMember(member);
        return "redirect:/members/login";
    }

    @Operation(summary = "로그인 폼", description = "로그인 화면으로 이동")
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("member", new LoginRequest());
        return "/member/loginForm";
    }

    @Operation(summary = "로그인 폼", description = "로그인 처리 후 홈 화면으로 이동한다")
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        HttpServletRequest request) throws Exception {
        MemberDto loginMember = memberService.login(loginRequest);
        if (loginMember != null) {
            //로그인 성공 처리
            HttpSession session = request.getSession();
            //세션에 로그인 회원 정보 보관
            session.setAttribute("loginMember", loginMember);
            session.setMaxInactiveInterval(60 * 30);
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
