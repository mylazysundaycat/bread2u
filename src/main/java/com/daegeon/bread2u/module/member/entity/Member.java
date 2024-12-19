package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.oauth.dto.KakaoUserInfoResponse;
import com.daegeon.bread2u.module.post.entity.Bread;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @OneToMany(mappedBy = "member")
    private List<Bread> breads = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();

    public Member(String email, String name, String profileImage, Platform platform) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.platform = platform;
    }

    public static Member from(KakaoUserInfoResponse kakaoUserInfoResponse) {
        Member member = new Member(kakaoUserInfoResponse.getKakaoAccount().getEmail(),
                kakaoUserInfoResponse.getKakaoAccount().getProfile().getNickname(),
                kakaoUserInfoResponse.getKakaoAccount().getProfile().getProfileImageUrl(),
                Platform.KAKAO);
        return member;
    }
}
