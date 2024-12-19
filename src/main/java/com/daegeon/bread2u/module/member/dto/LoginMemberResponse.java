package com.daegeon.bread2u.module.member.dto;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Platform;
import com.daegeon.bread2u.module.member.entity.Role;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberResponse {
    private String email;
    private String name;
    private String profileImage;
    private String platform;
    private String role;
    private List<Scrap> scraps;

    public LoginMemberResponse(String email, String name, String profileImage, String platform, String role, List<Scrap> scraps) {
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
                member.getScraps()
        );
    }
}
