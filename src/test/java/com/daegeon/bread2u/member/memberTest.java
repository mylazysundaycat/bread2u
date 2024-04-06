package com.daegeon.bread2u.member;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Role;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.module.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class memberTest {
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberService memberService;

    Member testMember = Member.builder()
            .username("test100")
            .email("cat@naver.com")
            .password("1234")
            .nickname("치즈고양이")
            .role(Role.MEMBER)
            .build();

    @Test
    @DisplayName("회원 로그인 테스트")
    void createMemberTest(){


    }
}
