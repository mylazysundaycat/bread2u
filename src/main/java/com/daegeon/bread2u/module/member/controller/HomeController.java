package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;
    @Operation(summary = "홈 화면", description = "홈 화면으로 이동한다")
    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       Model model) {
        loginService.addLoginMember(userDetails, model);
        return "/index";
    }
}
