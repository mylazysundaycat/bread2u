package com.daegeon.bread2u.module.oauth.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class KakaoUserInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;


    @Getter
    @Setter
    public static class KakaoAccount{
        private String email;
        private Profile profile;

        @Getter
        @Setter
        public static class Profile{
            private String nickname;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }

    }

}
