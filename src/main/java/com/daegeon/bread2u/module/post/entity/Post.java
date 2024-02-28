package com.daegeon.bread2u.module.post.entity;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.file.entity.File;
import com.daegeon.bread2u.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    @ColumnDefault("0")
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "file_id") //수정 요망
    private File file;

    @Builder
    public Post(String title, String content, Long likes, File file){
        this.title=title;
        this.content=content;
        this.likes=likes;
        this.file=file;
    }

}
