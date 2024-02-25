package com.daegeon.bread2u.domain.service;


import com.daegeon.bread2u.domain.entity.Member;
import com.daegeon.bread2u.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //create
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    //read
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //update
    //TODO EXCEPTION
    public Member updateMember(Long memberId, Member member) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow();
        Optional.ofNullable(findMember.getBirth())
                .ifPresent(birth -> findMember.setBirth(birth));
        Optional.ofNullable(findMember.getNickname())
                .ifPresent(nickname -> findMember.setNickname(nickname));
        Optional.ofNullable(findMember.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(findMember.getPassword())
                .ifPresent(password->findMember.setPassword(password));
        return memberRepository.save(findMember);
    }

    //delete
    //TODO EXCEPTION
    public void deleteMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow();
        memberRepository.delete(findMember);
    }
}
