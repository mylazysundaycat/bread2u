package com.daegeon.bread2u.module.comment.controller;


import com.daegeon.bread2u.module.comment.entity.CommentRequestDto;
import com.daegeon.bread2u.module.comment.service.CommentService;
import com.daegeon.bread2u.module.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final LoginService loginService;
    @PostMapping("/{postId}")
    public String createComment(@PathVariable Long postId,
                                @ModelAttribute CommentRequestDto comment,
                                Model model, HttpServletRequest request) {
        //SignUpRequestDto loginMember = loginService.loginValidation(model, request);
        //commentService.createComment(postId, comment, loginMember);
        return "redirect:/post/{postId}";
    }

}
