package com.daegeon.bread2u.module.memo.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.memo.dto.MemoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Memo extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "bakery_id")
    private Bakery bakery;

    public Memo(String content, Member member, Bakery bakery) {
        this.content = content;
        this.member = member;
        this.bakery = bakery;
    }
}
