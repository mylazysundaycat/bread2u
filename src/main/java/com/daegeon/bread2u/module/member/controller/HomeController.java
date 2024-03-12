package com.daegeon.bread2u.module.member.controller;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        //세션이 없으면 home
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/index";
        }
        //세션에 회원 데이터가 없으면 home
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "/index";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("loginmember", loginMember);
        return "/index";
    }
}
