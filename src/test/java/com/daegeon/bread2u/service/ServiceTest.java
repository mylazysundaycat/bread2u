package com.daegeon.bread2u.service;

import com.daegeon.bread2u.module.bakery.repository.BakeryRepository;
import com.daegeon.bread2u.module.bakery.service.BakeryService;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.memo.repository.MemoRepository;
import com.daegeon.bread2u.module.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public abstract class ServiceTest {
    @Autowired
    protected MemoRepository memoRepository;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected BakeryRepository bakeryRepository;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected MemoService memoService;
    @Autowired
    protected BakeryService bakeryService;
}
