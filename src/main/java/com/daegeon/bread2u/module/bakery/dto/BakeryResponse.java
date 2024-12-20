package com.daegeon.bread2u.module.bakery.dto;

import com.daegeon.bread2u.module.bakery.entity.Bakery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BakeryResponse {
    private Long id;
    private String storeName;
    private String address;
    private String roadAddress;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String standardDate;

    public BakeryResponse(final Long id, final String storeName, final String address, final String roadAddress, final String phone,
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

    public static BakeryResponse from(Bakery bakery) {
        BakeryResponse bakeryResponse = new BakeryResponse(
                bakery.getId(),
                bakery.getStoreName(),
                bakery.getAddress(),
                bakery.getRoadAddress(),
                bakery.getPhone(),
                bakery.getLatitude(),
                bakery.getLongitude(),
                bakery.getStandardDate()
        );
        return bakeryResponse;
    }
}
