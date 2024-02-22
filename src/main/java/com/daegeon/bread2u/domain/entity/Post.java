package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시물 엔티티
 */
@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private Long likes;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bread_id")
    private Bread bread;


}
