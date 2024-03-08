package com.daegeon.bread2u.module.member.service;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByMemberName(memberName)
                .orElseThrow(() -> new UsernameNotFoundException("없는 회원인듯.."));
        return User.builder()
                .username(member.getMemberName())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
