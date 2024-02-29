package com.daegeon.bread2u.module.file.entity;


import com.daegeon.bread2u.module.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter @Setter
@NoArgsConstructor
public class File {
    @Id @GeneratedValue
    @Column(name="file_id")
    private Long id;

    @Comment(value = "원본 파일명")
    private String originName;

    @Comment(value = "DB에 저장될 파일명")
    private String uploadName;

    @Comment(value = "확장자")
    private String extension;

    @Comment(value = "저장될 경로")
    private String path;

    @Comment(value = "매핑된 게시물")
    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    private Post post;
    @Builder
    public File(String originName, String uploadName, String extension, String path){
        this.originName = originName;
        this.uploadName = uploadName;
        this.extension = extension;
        this.path = path;
    }
}
