package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.module.member.repository.LoginRequestDto;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/members")
public class testController {
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
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("member", new LoginRequestDto());
        return "/member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto,
                           BindingResult bindingResult,
                           HttpServletRequest request) throws Exception {

        if (bindingResult.hasErrors()) {return "redirect:/";}

        MemberDto loginMember = memberService.login(loginRequestDto);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "redirect:/";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
