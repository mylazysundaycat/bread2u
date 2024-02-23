package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
public class Bread {
    @Id
    @GeneratedValue
    @Column(name = "bread_id")
    private Long id;
    private String title;
    private Long price;
    private String detail;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "bread")
    private List<Comment> comments;
}
