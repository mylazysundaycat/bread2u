package com.daegeon.bread2u.module.post.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;
    private MultipartFile file;
}
