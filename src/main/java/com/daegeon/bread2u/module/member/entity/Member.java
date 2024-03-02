package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.shop.entity.Bread;
import com.daegeon.bread2u.module.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String memberRegisterId;

    private String email;

    private String password;

    private String nickname;

//    private LocalDateTime birth;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Shop> shops;
    @OneToMany(mappedBy = "member")
    private List<Bread> bread;
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;


}
