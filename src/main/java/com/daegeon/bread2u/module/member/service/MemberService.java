package com.daegeon.bread2u.module.member.service;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.global.jwt.JwtProvider;
import com.daegeon.bread2u.global.jwt.dto.TokenResponse;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import com.daegeon.bread2u.module.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;
    private final JwtProvider jwtProvider;

    public boolean isMember(String email) {
        if (memberRepository.findByEmail(email) == null) return false;
        return true;
    }

    public void createMember(Member member) {
        memberRepository.save(member);
    }

    public Member getMemberByEmail(String email) {
        Member member =  memberRepository.findByEmail(email);
        member.setScraps(getMemberScraps(email));
        return member;
    }
    public List<Scrap> getMemberScraps(String email) {
        return scrapRepository.findAllByMemberEmail(email);
    }
    public TokenResponse login(String email) {
        return new TokenResponse("Bearer "+jwtProvider.CreateToken(email));
    }

}
