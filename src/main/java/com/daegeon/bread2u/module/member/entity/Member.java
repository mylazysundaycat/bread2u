package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.global.BaseTimeEntity;
import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.member.repository.dto.SignUpRequestDto;
import com.daegeon.bread2u.module.shop.entity.Bread;
import com.daegeon.bread2u.module.shop.entity.Shop;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Shop> shops;
    @OneToMany(mappedBy = "member")
    private List<Bread> bread;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    @Operation(summary = "회원가입 편의 메소드")
    public static Member from(SignUpRequestDto signUpRequestDto) {
        return Member.builder()
                .username(signUpRequestDto.getUsername())
                .nickname(signUpRequestDto.getNickname())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .build();
    }


}
