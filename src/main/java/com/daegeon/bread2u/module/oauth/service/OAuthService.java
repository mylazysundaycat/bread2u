package com.daegeon.bread2u.module.oauth.service;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.oauth.dto.KakaoUserInfoResponse;
import com.daegeon.bread2u.global.jwt.response.AccessTokenResponse;
import com.daegeon.bread2u.global.jwt.response.TokenReseponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class OAuthService {
    private final MemberService memberService;
    @Value("${kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    public TokenReseponse processKakaoLogin(final String code) {
        String accessToken = getKakaoAccessToken(code).getAccessToken();
        KakaoUserInfoResponse kakaoUserInfoResponse = getKakaoUserInfo(accessToken);
        return loginAfterMemberCheck(kakaoUserInfoResponse);
    }
    public AccessTokenResponse getKakaoAccessToken(final String code) {
        // Authorization Server와 통신 - accessToken을 받아오기 위함
        WebClient authorizationServer = WebClient.builder()
                .baseUrl("https://kauth.kakao.com/oauth/token")
                .build();

        return authorizationServer.post().uri(uriBuilder -> uriBuilder
                .queryParam("grant_type","authorization_code")
                        .queryParam("client_id", KAKAO_CLIENT_ID)
                        .queryParam("redirect_uri", KAKAO_REDIRECT_URI)
                        .queryParam("code", code)
                .build()
        ).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .block();
    }

    public KakaoUserInfoResponse getKakaoUserInfo(String accessToken) {
        // Resource Server와의 통신 - UserInfo를 받아오기 위함
        WebClient resourceServer = WebClient.builder()
                .baseUrl("https://kapi.kakao.com/v2/user/me")
                .build();

        return resourceServer.get().uri(uriBuilder -> uriBuilder
                .queryParam("secure_resource", "true").build())
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }

    public TokenReseponse loginAfterMemberCheck(KakaoUserInfoResponse kakaoUserInfoResponse) {
        String email = kakaoUserInfoResponse.getKakaoAccount().getEmail();
        if (!memberService.isMember(email)) {
            memberService.createMember(Member.from(kakaoUserInfoResponse));
        }
        return memberService.login(email);
    }
}
