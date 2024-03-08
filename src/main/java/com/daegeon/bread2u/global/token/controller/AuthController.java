package com.daegeon.bread2u.global.token.controller;

import com.daegeon.bread2u.global.jwt.TokenProvider;
import com.daegeon.bread2u.global.token.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.global.token.service.AuthService;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인 폼", description = "로그인 페이지로 이동한다")
    @GetMapping
    public String loginMember(Model model) {
        model.addAttribute("member", new LoginRequestDto());
        return "/member/loginForm";
    }
    @Operation(summary = "로그인", description = "로그인을 하고 홈 화면으로 이동한다.")
    @Transactional
    @PostMapping("/process")
    public String authorize(@ModelAttribute LoginRequestDto loginRequestDto, Model model) {
        authService.login(loginRequestDto);
        model.addAttribute("token", )
        return "redirect:/index";
    }
}
