package com.daegeon.bread2u.module.member.service;


import com.daegeon.bread2u.module.member.repository.LoginRequestDto;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public MemberDto login(final LoginRequestDto loginRequestDto) throws Exception {
        // 1. 회원 정보 및 비밀번호 조회
        Member findedMember = memberRepository.findOneByMembername(loginRequestDto.getMembername())
                .orElseThrow(()->new Exception("회원 정보가 존재하지 않습니다."));
        String encodedPassword = findedMember.getPassword();


        // 3. 회원 응답 객체에서 비밀번호를 제거한 후 회원 정보 리턴
        return MemberDto.from(loginRequestDto);
    }


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }


    public Member updateMember(Long memberId, Member member) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow();
//        Optional.ofNullable(findMember.getBirth())
//                .ifPresent(birth -> findMember.setBirth(birth));
        Optional.ofNullable(findMember.getNickname())
                .ifPresent(nickname -> findMember.setNickname(nickname));
        Optional.ofNullable(findMember.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(findMember.getPassword())
                .ifPresent(password->findMember.setPassword(password));
        return memberRepository.save(findMember);
    }

    public void deleteMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow();
        memberRepository.delete(findMember);
    }
}
