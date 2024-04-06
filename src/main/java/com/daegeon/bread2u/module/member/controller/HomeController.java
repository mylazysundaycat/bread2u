package com.daegeon.bread2u.module.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Operation(summary = "홈 화면", description = "홈 화면으로 이동한다")
    @GetMapping("/")
    public String home(Model model) {
        return "/index";
    }
}
