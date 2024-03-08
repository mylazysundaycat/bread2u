package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.member.repository.dto.MemberDto;
import com.daegeon.bread2u.module.shop.entity.Bread;
import com.daegeon.bread2u.module.shop.entity.Shop;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String memberName;
    private String nickname;
    private String email;
    private String password;

//    private LocalDateTime birth;
//   @Enumerated(EnumType.STRING)
    private String role;

    @OneToMany(mappedBy = "member")
    private List<Shop> shops;
    @OneToMany(mappedBy = "member")
    private List<Bread> bread;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    @Operation(summary = "회원가입 편의 메소드")
    public static Member from(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberName(memberDto.getMemberName())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .role(memberDto.getRole())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .build();
    }
}
