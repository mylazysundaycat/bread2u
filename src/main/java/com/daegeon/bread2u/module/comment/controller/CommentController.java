package com.daegeon.bread2u.module.comment.controller;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.comment.entity.CommentRequestDto;
import com.daegeon.bread2u.module.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{postId}")
    public String createComment(@PathVariable Long postId,
                                @ModelAttribute CommentRequestDto comment) {
        commentService.createComment(postId, comment);
        return "redirect:/post/{postId}";
    }

}
