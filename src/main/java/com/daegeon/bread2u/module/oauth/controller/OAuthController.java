package com.daegeon.bread2u.module.oauth.controller;


import com.daegeon.bread2u.module.token.TokenReqeust;
import com.daegeon.bread2u.module.token.TokenReseponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class OAuthController {

    @Value("${kakao.uri}")
    private String kakaoUri;
    @GetMapping("/kakao/login")
    public ResponseEntity<Void> OAuth2LoginRedirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kakaoUri));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

//    @PostMapping("/kakao/callback")
//    public ResponseEntity<TokenReseponse> kakaoCallback(@RequestBody TokenReqeust tokenReqeust) {
//
//    }

//
//    @PostMapping("/google/callback")
//    public ResponseEntity<TokenReseponse> googleLogin(@RequestBody TokenReqeust tokenReqeust) {
//
//    }
}
