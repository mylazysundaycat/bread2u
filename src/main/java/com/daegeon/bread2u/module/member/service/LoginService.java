package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.module.member.repository.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginService {
    HttpSession session;
    public MemberDto loginValidation(final Model model, final HttpServletRequest request){
        session = request.getSession(false);
        if(session==null) {
            model.addAttribute("loginmember", null);
            return null;
        }
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        if(loginMember!=null) model.addAttribute("loginmember", loginMember);
        else model.addAttribute("loginmember", null);

        return loginMember;
    }
}
