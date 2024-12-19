package com.daegeon.bread2u.module.oauth.controller;


import com.daegeon.bread2u.module.oauth.service.OAuthService;
import com.daegeon.bread2u.global.jwt.TokenReqeust;
import com.daegeon.bread2u.global.jwt.TokenReseponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    @Value("${kakao.uri}")
    private String kakaoUri;
    @GetMapping("/kakao/login")
    public ResponseEntity<Void> OAuth2LoginRedirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kakaoUri));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    @PostMapping("/kakao/callback")
    public ResponseEntity<TokenReseponse> kakaoCallback(@RequestBody TokenReqeust tokenReqeust) {
        TokenReseponse tokenReseponse = oAuthService.processKakaoLogin(tokenReqeust);
        return new ResponseEntity<>(tokenReseponse, HttpStatus.OK);
    }
}