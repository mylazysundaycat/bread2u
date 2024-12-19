package com.daegeon.bread2u.module.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BakeryRequest {
    private String storeName;
    private String address;
    private String roadAddress;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String standardDate;
}