package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_id")
    private Bread bread;
}
