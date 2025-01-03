package com.daegeon.bread2u.module.scrap.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.bakery.entity.Bakery;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bakery_id")
    private Bakery bakery;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Scrap(Bakery bakery, Member member) {
        this.bakery = bakery;
        this.member = member;
    }
}
