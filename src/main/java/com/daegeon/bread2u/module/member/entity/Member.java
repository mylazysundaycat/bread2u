package com.daegeon.bread2u.module.member.entity;


import com.daegeon.bread2u.global.common.BaseTimeEntity;
import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.post.entity.Bread;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    @OneToMany(mappedBy = "member")
    private List<Bread> breads = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Scrap> scraps = new ArrayList<>();
}
