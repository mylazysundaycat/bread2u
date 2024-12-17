package com.daegeon.bread2u.module.comment.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.post.entity.Bread;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "bread_id")
    private Bread bread;
}
