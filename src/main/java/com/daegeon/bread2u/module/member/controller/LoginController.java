package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.dto.SignUpRequestDto;
import com.daegeon.bread2u.module.member.service.LoginService;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Login", description = "Login API")
@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {
    private final MemberService memberService;
    private final LoginService loginService;

    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping("/signup")
    public String createMember(Model model) {
        model.addAttribute("member", new LoginRequestDto());
        return "/member/createMemberForm";
    }

    @Operation(summary = "회원가입", description = "회원가입을 정상적으로 마치고, 로그인 화면으로 이동한다.")
    @PostMapping("/signup")
    public String join(@ModelAttribute SignUpRequestDto memberDto) {
        Member member = Member.from(memberDto);
        memberService.createMember(member);
        return "redirect:/members/login";
    }


    @GetMapping("/signIn")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        /*이미 로그인된 사용자일 경우 인덱스 페이지로 강제이동.*/
        if (userDetails != null) {
            log.info(userDetails.getMember().getUsername() +
                    "님이 로그인 페이지로 이동을 시도함. -> index 페이지로 강제 이동 함.");
            return "redirect:/";
        }
        return "/member/loginForm";
    }
    @PostMapping("/api/signIn")
    public String login(LoginRequestDto request, HttpServletResponse response) {
        loginService.login(request, response);
        return "redirect:/";
    }

//    @Operation(summary = "로그인 폼", description = "로그인 화면으로 이동")
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("member", new LoginRequestDto());
//        return "/member/loginForm";
//    }
//
//    @Operation(summary = "로그인 폼", description = "로그인 처리 후 홈 화면으로 이동한다")
//    @PostMapping("/login-process")
//    public String login(@ModelAttribute LoginRequestDto loginRequestDto,
//                        HttpServletRequest request) throws Exception {
//        SignUpRequestDto loginMember = memberService.login(loginRequestDto);
//        if (loginMember != null) {
//            //로그인 성공 처리
//            HttpSession session = request.getSession();
//            //세션에 로그인 회원 정보 보관
//            session.setAttribute("loginMember", loginMember);
//            session.setMaxInactiveInterval(60 * 30);
//        }
//        return "redirect:/";
//    }

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
