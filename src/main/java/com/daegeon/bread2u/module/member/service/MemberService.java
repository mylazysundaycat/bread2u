package com.daegeon.bread2u.module.member.service;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.global.jwt.JwtProvider;
import com.daegeon.bread2u.global.jwt.response.TokenReseponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    public boolean isMember(String email) {
        if (memberRepository.findByEmail(email) == null) return false;
        return true;
    }

    public void createMember(Member member) {
        memberRepository.save(member);
    }

    public Long findMemberId(String email) {
        return memberRepository.findIdByEmail(email);
    }

    public TokenReseponse login(String email) {
        return new TokenReseponse("Bearer "+jwtProvider.CreateToken(email));
    }
}
