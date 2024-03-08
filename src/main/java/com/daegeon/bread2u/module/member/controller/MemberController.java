package com.daegeon.bread2u.module.member.controller;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.dto.MemberDto;
import com.daegeon.bread2u.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @Operation(summary = "회원가입 폼", description = "회원가입 페이지로 이동한다")
    @GetMapping
    public String createMember(Model model) {
        model.addAttribute("member", new MemberDto());
        return "/member/createMemberForm";
    }
    @Operation(summary = "회원가입", description = "회원가입을 정상적으로 마치고, 로그인 화면으로 이동한다.")
    @PostMapping
    public String join(@ModelAttribute MemberDto memberDto) {
        Member member = Member.from(memberDto, passwordEncoder);
        memberService.createMember(member);
        return "redirect:/member/login";
    }

    @Operation(summary = "로그인 폼", description = "로그인 페이지로 이동한다")
    @GetMapping("/login")
    public String loginMember(Model model) {
        model.addAttribute("member", new MemberDto());
        return "/member/loginForm";
    }

//    @Operation(summary = "로그인 폼", description = "로그인 페이지로 이동한다")
//    @PostMapping("/login")
//    public String loginMember(@ModelAttribute Member member) {
//        return "redirect:/index";
//    }

    //readAll
    @Operation(summary = "회원 리스트 페이지", description = "회원 리스트 페이지로 이동한다")
    @GetMapping("/list")
    public String findMembers(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "/member/memberList";
    }

    //read
    @Operation(summary = "회원 마이 페이지", description = "회원 마이 페이지로 이동한다")
    @GetMapping("/{memberId}")
    public String updateMember(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.findById(memberId));
        return "/member/MyPage";
    }

    //update
    @Operation(summary = "회원정보 수정", description = "회원 정보를 수정한다")
    @PostMapping("/{memberId}")
    public String udpateMember(@PathVariable Long memberId, @ModelAttribute Member member) {
        memberService.updateMember(memberId, member);
//        return "ok";
        return "redirect:/member/MyPage";
    }

    //delete
    @Operation(summary = "회원 삭제", description = "회원을 삭제한다.")
    @GetMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
//        return "ok";
        return "redirect:/member";
    }
}
