package com.daegeon.bread2u.module.memo.controller;


import com.daegeon.bread2u.global.auth.Auth;
import com.daegeon.bread2u.module.member.dto.LoginMemberRequest;
import com.daegeon.bread2u.module.memo.dto.MemoRequest;
import com.daegeon.bread2u.module.memo.dto.MemoResponse;
import com.daegeon.bread2u.module.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {
    private final MemoService memoService;
    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(@Auth LoginMemberRequest loginMemberRequest,
                                                   @RequestBody MemoRequest memoRequest) {
        MemoResponse memoResponse = memoService.createMemo(memoRequest, loginMemberRequest.getEmail());
        return new ResponseEntity<>(memoResponse, HttpStatus.OK);
    }
}
