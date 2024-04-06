package com.daegeon.bread2u.module.member.service;


import com.daegeon.bread2u.global.exception.Bread2uException;
import com.daegeon.bread2u.global.exception.ErrorCode;
import com.daegeon.bread2u.module.member.repository.dto.LoginRequestDto;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.dto.SignUpRequestDto;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param member
     * @return
     */
    public Member createMember(Member member) {
        //이메일 중복검사
        duplicateEmail(member);
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        return memberRepository.save(member);
    }

    /**
     * 회원가입시 이메일 중복 검사
     * @param member
     */
    public void duplicateEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new Bread2uException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    /**
     * 로그인 기능
     * @param loginRequestDto
     * @return
     */
    public SignUpRequestDto login(final LoginRequestDto loginRequestDto)  {
        //1. loginRequestDto로 회원 객체를 찾아온다.
        Member findedMember = memberRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(()->new Bread2uException(ErrorCode.NOT_FOUND_MEMBER));
        //2. loginRequestDto로 들어온 비밀번호와 DB내의 비밀번호를 비교한다.
        if (loginRequestDto.getPassword().equals(findedMember.getPassword())) {
            return SignUpRequestDto.fromEntity(findedMember);
        }else{
            throw new Bread2uException(ErrorCode.MISMATCHED_EMAIL_OR_PASSWORD);
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
    public Optional<Member> findByMembername(String membername){
        return memberRepository.findByUsername(membername);
    }

    public Member updateMember(Long memberId, Member member) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow();
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
