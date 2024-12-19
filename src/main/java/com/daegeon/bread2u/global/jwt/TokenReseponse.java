package com.daegeon.bread2u.global.jwt;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class TokenReseponse {
    private String accessToken;

    public TokenReseponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
