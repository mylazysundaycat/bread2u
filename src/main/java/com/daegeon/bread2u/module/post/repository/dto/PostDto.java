package com.daegeon.bread2u.module.post.repository.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
