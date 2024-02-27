package com.daegeon.bread2u.module.shop.entity;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.shop.entity.Bread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Shop {
    @Id
    @GeneratedValue
    @Column(name = "shop_id")
    private Long id;
    private String title;
    private String detail;
    private String region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    @OneToMany(mappedBy = "shop")
    private List<Bread> bread;
}
