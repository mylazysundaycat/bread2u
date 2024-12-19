package com.daegeon.bread2u.module.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
