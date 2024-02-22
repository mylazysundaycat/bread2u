package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
