package com.daegeon.bread2u.module.shop.entity;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Builder
public class Bread {
    @Id
    @GeneratedValue
    @Column(name = "bread_id")
    private Long id;
    private String title;
    private Long price;
    private String detail;
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @OneToMany(mappedBy = "bread")
    private List<Comment> comments;
}
