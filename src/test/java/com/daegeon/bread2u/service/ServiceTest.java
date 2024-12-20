package com.daegeon.bread2u.service;

import com.daegeon.bread2u.module.bakery.service.BakeryService;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected MemoService memoService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected BakeryService bakeryService;

}
