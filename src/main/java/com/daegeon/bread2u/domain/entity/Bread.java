package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
    private List<Comment> posts;
}
