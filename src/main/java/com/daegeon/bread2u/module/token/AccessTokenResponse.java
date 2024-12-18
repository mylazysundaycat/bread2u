package com.daegeon.bread2u.module.token;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponse {
    private String access_token;
}
