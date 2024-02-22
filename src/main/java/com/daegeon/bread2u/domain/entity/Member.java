package com.daegeon.bread2u.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

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

    private Long age;

    @Enumerated(EnumType.STRING)
    private Role role;
}
