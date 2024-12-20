package com.daegeon.bread2u.module.member.dto;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.scrap.dto.ScrapResponse;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberResponse {
    private String email;
    private String name;
    private String profileImage;
    private String platform;
    private String role;
    private List<ScrapResponse> scraps;

    public LoginMemberResponse(String email, String name, String profileImage, String platform, String role, List<ScrapResponse> scraps) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.platform = platform;
        this.role = role;
        this.scraps = scraps;
    }

    public static LoginMemberResponse from(Member member) {
        return new LoginMemberResponse(
                member.getEmail(),
                member.getName(),
                member.getProfileImage(),
                member.getPlatform().name(),
                member.getRole().name(),
                member.getScraps().stream()
                        .map(ScrapResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
