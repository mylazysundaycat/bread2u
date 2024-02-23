package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Date birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Shop> shops;
    @OneToMany(mappedBy = "member")
    private List<Bread> bread;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;


}
