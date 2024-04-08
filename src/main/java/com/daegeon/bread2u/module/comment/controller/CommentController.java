package com.daegeon.bread2u.module.comment.controller;


import com.daegeon.bread2u.module.comment.entity.CommentRequestDto;
import com.daegeon.bread2u.module.comment.service.CommentService;
import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model) {
        loginService.addLoginMember(userDetails, model);
        commentService.createComment(postId, comment, userDetails.getMember());
        return "redirect:/post/{postId}";
    }

    @PutMapping("/{postId}")
    public String updateComment(@PathVariable Long postId,
                                @ModelAttribute CommentRequestDto comment,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model) {
        loginService.addLoginMember(userDetails, model);
        commentService.updateComment(comment);
        return "redirect:/post/{postId}";
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model){
        loginService.addLoginMember(userDetails, model);
        commentService.deleteComment(commentId);
    }
}
