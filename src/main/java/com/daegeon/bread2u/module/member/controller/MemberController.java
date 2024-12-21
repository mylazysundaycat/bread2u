package com.daegeon.bread2u.module.member.controller;


import com.daegeon.bread2u.global.auth.Auth;
import com.daegeon.bread2u.module.member.dto.LoginMemberRequest;
import com.daegeon.bread2u.module.member.dto.LoginMemberResponse;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.memo.entity.Memo;
import com.daegeon.bread2u.module.memo.service.MemoService;
import com.daegeon.bread2u.module.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemoService memoService;
    private final ScrapService scrapService;
    @GetMapping
    public ResponseEntity<LoginMemberResponse> getLoginMemberInfo(@Auth LoginMemberRequest loginMemberRequest) {
        Member member = memberService.getMemberByEmail(loginMemberRequest.getEmail());
        member.setScraps(scrapService.getMemberScraps(member.getEmail()));
        member.setMemos(memoService.getMemberMemo(member.getEmail()));
        LoginMemberResponse loginMemberResponse = LoginMemberResponse.from(member);
        return new ResponseEntity<>(loginMemberResponse, HttpStatus.OK);
    }
}
