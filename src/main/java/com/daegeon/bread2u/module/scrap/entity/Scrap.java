package com.daegeon.bread2u.module.scrap.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.post.entity.Bread;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Bread bread;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}