package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.global.BaseTimeEntity;
import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.member.repository.LoginRequest;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.shop.entity.Bread;
import com.daegeon.bread2u.module.shop.entity.Shop;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String membername;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Shop> shops;
    @OneToMany(mappedBy = "member")
    private List<Bread> bread;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    @Operation(summary = "회원가입 편의 메소드")
    public static Member from(LoginRequest memberDto) {
        return Member.builder()
                .membername(memberDto.getMembername())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .build();
    }

    @Operation(summary = "회원가입 편의 메소드")
    public static Member from(MemberDto memberDto) {
        return Member.builder()
                .membername(memberDto.getMembername())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .build();
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
