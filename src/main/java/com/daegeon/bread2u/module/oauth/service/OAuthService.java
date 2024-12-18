package com.daegeon.bread2u.module.oauth.service;

import com.daegeon.bread2u.module.token.AccessTokenResponse;
import com.daegeon.bread2u.module.token.TokenReqeust;
import com.daegeon.bread2u.module.token.TokenReseponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class OAuthService {

    public AccessTokenResponse getKakaoAccessToken(final TokenReqeust tokenReqeust) {
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
}
