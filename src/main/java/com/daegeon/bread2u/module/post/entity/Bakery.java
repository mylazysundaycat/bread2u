package com.daegeon.bread2u.module.post.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
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
    @GeneratedValue
    private Long id;
    private String storeName;
    private String address; // 지번
    private String roadAddress; // 도로명
    private String phone;
    private Double latitude;
    private Double longitude;
    private String standardDate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "bakery")
    private List<Scrap> scraps;

    public Bakery(final String storeName, final String address, final String roadAddress, final String phone, final Double latitude,
                  final Double longitude, final String standardDate) {
        this.storeName = storeName;
        this.address = address;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.standardDate = standardDate;
    }
}
