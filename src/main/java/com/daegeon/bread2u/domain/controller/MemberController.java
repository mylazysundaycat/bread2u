package com.daegeon.bread2u.domain.controller;


import com.daegeon.bread2u.domain.entity.Member;
import com.daegeon.bread2u.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    //create
    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping("/member")
    public String createMember() {
        return "/member/RegisterForm";
    }

    @Operation(summary = "회원가입", description = "회원가입을 한다")
    @PostMapping("/member/create")
    public String createMember(@ModelAttribute Member member) {
        memberService.createMember(member);
        return "redirect:/";
    }

    //read
    @Operation(summary = "마이 페이지", description = "마이 페이지로 이동한다")
    @GetMapping("/member/{memberId}")
    public String updateMember(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.findById(memberId));
        return "/member/MyPage";
    }

    //update
    @Operation(summary = "회원정보 수정", description = "회원 정보를 수정한다")
    @PostMapping("/member/{memberId}")
    public String udpateMember(@PathVariable Long memberId, @ModelAttribute Member member) {

        return "redirect:/member/MyPage";
    }

    //delete

}
