package com.daegeon.bread2u.module.oauth.service;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.oauth.dto.KakaoUserInfoResponse;
import com.daegeon.bread2u.global.jwt.AccessTokenResponse;
import com.daegeon.bread2u.global.jwt.TokenReqeust;
import com.daegeon.bread2u.global.jwt.TokenReseponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class OAuthService {
    private final MemberService memberService;

    public TokenReseponse processKakaoLogin(final TokenReqeust tokenReqeust) {
        String accessToken = getKakaoAccessToken(tokenReqeust).getAccessToken();
        KakaoUserInfoResponse kakaoUserInfoResponse = getKakaoUserInfo(accessToken);
        return loginAfterMemberCheck(kakaoUserInfoResponse);
    }
    public AccessTokenResponse getKakaoAccessToken(final TokenReqeust tokenReqeust) {
        // Authorization Server와 통신 - accessToken을 받아오기 위함
        WebClient authorizationServer = WebClient.builder()
                .baseUrl("https://kauth.kakao.com/oauth/token")
                .build();

        return authorizationServer.post().uri(uriBuilder -> uriBuilder
                .queryParam("grant_type","authorization_code")
                        .queryParam("client_id", "kakaoClientId")
                        .queryParam("redirect_uri", "redirectUri")
                        .queryParam("code", tokenReqeust.getCode())
                        .queryParam("client_secret", "kakaoClientSecret")
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

        return resourceServer.post().uri(uriBuilder -> uriBuilder
                .queryParam("secure_resource", "true").build())
                .header("Authorization", "Bearer "+accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
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
