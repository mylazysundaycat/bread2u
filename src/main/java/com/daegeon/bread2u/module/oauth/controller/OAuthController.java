package com.daegeon.bread2u.module.oauth.controller;


import com.daegeon.bread2u.global.jwt.dto.TokenReqeust;
import com.daegeon.bread2u.module.oauth.service.OAuthService;
import com.daegeon.bread2u.global.jwt.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Value("${home-uri}")
    private String homeUri;

    @GetMapping("/kakao/login")
    public ResponseEntity<Void> OAuth2LoginRedirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kakaoUri));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        TokenResponse tokenResponse = oAuthService.processKakaoLogin(code);
        // JavaScript로 메인 페이지 리다이렉트
        String script = "<script>"
                + "localStorage.setItem('token', '" + tokenResponse.getAccessToken() + "');"
                + "window.location.href = '"+homeUri+ "';"
                + "</script>";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(script, headers, HttpStatus.OK);
    }

//    @GetMapping("/kakao/login")
//    public ResponseEntity<String> OAuth2LoginRedirect() {
//        return new ResponseEntity<>(kakaoUri, HttpStatus.OK);
//    }
//    @PostMapping("/kakao/callback")
//    public ResponseEntity<TokenResponse> kakaoCallback(@RequestBody TokenReqeust tokenReqeust) {
//        TokenResponse tokenResponse = oAuthService.processKakaoLogin(tokenReqeust.getCode());
//        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
//    }

}
