package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.module.member.repository.LoginRequestDto;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class LoginController {
    private final MemberService memberService;
    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping
    public String createMember(Model model) {
        model.addAttribute("member", new LoginRequestDto());
        return "/member/createMemberForm";
    }
    @Operation(summary = "회원가입", description = "회원가입을 정상적으로 마치고, 로그인 화면으로 이동한다.")
    @PostMapping
    public String join(@ModelAttribute LoginRequestDto memberDto) {
        Member member = Member.from(memberDto);
        memberService.createMember(member);
        return "redirect:/members/login";
    }
    @Operation(summary = "로그인 폼", description = "로그인 화면으로 이동")
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("member", new LoginRequestDto());
        return "/member/loginForm";
    }
    //TODO 추후에 타임리프로 validation 추가
    @Operation(summary = "로그인 폼", description = "로그인 화면으로 이동")
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto,
                           HttpServletRequest request) throws Exception {
        MemberDto loginMember = memberService.login(loginRequestDto);
        //TODO 변경해야할 지점
        if (loginMember == null) {
            return "아이엠오류에요";
        }
        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {session.invalidate();}
        return "redirect:/";
    }
}
