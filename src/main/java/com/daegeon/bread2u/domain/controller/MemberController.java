package com.daegeon.bread2u.domain.controller;


import com.daegeon.bread2u.domain.entity.Member;
import com.daegeon.bread2u.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //create
    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping("/member")
    public String createMember() {
        return "/member/createMemberForm";
    }

    @Operation(summary = "회원가입", description = "회원가입을 한다")
    @PostMapping("/member")
    public String createMember(@ModelAttribute Member memberForm) {
        memberService.createMember(memberForm);
//        return "ok";
        return "redirect:/";
    }

    //readAll
    @Operation(summary = "회원 리스트 페이지", description = "회원 리스트 페이지로 이동한다")
    @GetMapping("/members")
    public String findMembers(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
//        return "ok";
        return "/member/memberList";
    }

    //read
    @Operation(summary = "회원 마이 페이지", description = "회원 마이 페이지로 이동한다")
    @GetMapping("/member/{memberId}")
    public String updateMember(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.findById(memberId));
        return "/member/MyPage";
    }

    //update
    @Operation(summary = "회원정보 수정", description = "회원 정보를 수정한다")
    @PostMapping("/member/{memberId}")
    public String udpateMember(@PathVariable Long memberId, @ModelAttribute Member member) {
        memberService.updateMember(memberId, member);
//        return "ok";
        return "redirect:/member/MyPage";
    }

    //delete
    @Operation(summary = "회원 삭제", description = "회원을 삭제한다.")
    @GetMapping("/member/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
//        return "ok";
        return "redirect:/member";
    }
}
