package com.daegeon.bread2u.module.comment.entity;


import com.daegeon.bread2u.global.BaseTimeEntity;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.shop.entity.Bread;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private Long likes;
    private Long parent_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_id")
    private Bread bread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content, Long likes, Post post, Member member){
        this.content=content;
        this.likes=likes;
        this.post=post;
        this.member=member;
    }
}
