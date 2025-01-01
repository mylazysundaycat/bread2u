package com.daegeon.bread2u.module.bakery.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bakery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeName;
    private String address; // 지번
    private String roadAddress; // 도로명
    private String phone;
    private Double latitude;
    private Double longitude;
    private String standardDate;

    @OneToMany(mappedBy = "bakery")
    private List<Scrap> scraps;

    public Bakery(String storeName, String address, String roadAddress, String phone, Double latitude,
                  Double longitude, String standardDate) {
        this.storeName = storeName;
        this.address = address;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.standardDate = standardDate;
    }

    public Bakery(Long id, String storeName, String address, String roadAddress, String phone,
                  Double latitude, Double longitude, String standardDate) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.standardDate = standardDate;
    }
}
