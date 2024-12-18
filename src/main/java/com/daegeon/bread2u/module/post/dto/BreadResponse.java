package com.daegeon.bread2u.module.post.dto;

import com.daegeon.bread2u.module.post.entity.Bread;
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
    private Double latitude;
    private Double longitude;
    private String standardDate;

    public BreadResponse(final Long id, final String storeName, final String address, final String roadAddress, final String phone,
                         final Double latitude, final Double longitude, final String standardDate) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.standardDate = standardDate;
    }

    public static BreadResponse from(Bread bread) {
        BreadResponse breadResponse = new BreadResponse(
                bread.getId(),
                bread.getStoreName(),
                bread.getAddress(),
                bread.getRoadAddress(),
                bread.getStandardDate(),
                bread.getLatitude(),
                bread.getLongitude(),
                bread.getStandardDate()
        );
        return breadResponse;
    }
}
