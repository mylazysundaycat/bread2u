package com.daegeon.bread2u.module.file.entity;


import com.daegeon.bread2u.module.post.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
public class FileDto {
    private Long id;

    @Comment(value = "원본 파일명")
    private String originName;

    @Comment(value = "DB에 저장될 파일명")
    private String uploadName;

    @Comment(value = "확장자")
    private String extension;

    @Comment(value = "저장될 경로")
    private String path;

    public File toEntity() {
        return File.builder()
                .originName(originName)
                .uploadName(uploadName)
                .extension(extension)
                .path(path)
                .build();
    }
}
