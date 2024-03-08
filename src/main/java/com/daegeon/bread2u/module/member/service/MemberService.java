package com.daegeon.bread2u.module.member.service;


import com.daegeon.bread2u.module.member.entity.Member;
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

    //유효성 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
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
