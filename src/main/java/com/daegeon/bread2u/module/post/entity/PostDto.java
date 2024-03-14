package com.daegeon.bread2u.module.post.entity;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private MultipartFile file;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
