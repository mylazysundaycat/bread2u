package com.daegeon.bread2u.module.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BreadResponse{
    private Long id;
    private String storeName;
    private String address;
    private String roadAddress;
    private String phone;
    private Long latitude;
    private Long longitude;
    private String standardDate;

    public BreadResponse from(ApiResponse apiResponse) {

    }
}
