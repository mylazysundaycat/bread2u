package com.daegeon.bread2u.module.comment.controller;


import com.daegeon.bread2u.module.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
