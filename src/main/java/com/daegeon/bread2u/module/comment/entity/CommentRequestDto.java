package com.daegeon.bread2u.module.comment.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private Long likes;

}
