package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.comment.service.CommentService;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.service.LoginService;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.entity.PostDto;
import com.daegeon.bread2u.module.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LoginService loginService;

    @Operation(summary = "전체 글 조회", description = "전제 게시물 목록을 최신순으로 조회합니다")
    @GetMapping
    public String findAll(Model model, HttpServletRequest request) {
        loginService.loginValidation(model, request);
        List<Post> posts = postService.findAll();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        return "/post/postList";
    }

    @Operation(summary = "특정 글 조회", description = "postId 값에 해당하는 게시물을 조회합니다")
    @GetMapping("/{postId}")
    public String findById(@PathVariable Long postId, Model model, HttpServletRequest request) {
        loginService.loginValidation(model, request);
        Post postFindById = postService.findById(postId).orElseThrow();
        List<Comment> commentFindedByPostId = commentService.findByPostId(postId);
        model.addAttribute("post", postFindById);
        model.addAttribute("comments", commentFindedByPostId);
        return "/post/postDetail";
    }

    @Operation(summary = "게시물 작성 폼", description = "게시물 작성 폼으로 이동합니다")
    @GetMapping("/create")
    public String createPost(Model model, HttpServletRequest request) {
        loginService.loginValidation(model, request);
        model.addAttribute("post", new PostDto());
        return "/post/createPostForm";
    }

    @Operation(summary = "게시물 작성", description = "게시물을 작성합니다")
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDto postDto
            , Model model, HttpServletRequest request) {
        MemberDto loginMember = loginService.loginValidation(model, request);
        postService.createPost(postDto, loginMember);
        return "redirect:/post";
    }

    @Operation(summary = "게시물 수정 폼", description = "게시물 수정 폼으로 이동합니다")
    @GetMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, Model model) {
        Post findByPostId = postService.findById(postId)
                .orElseThrow();
        model.addAttribute("post", findByPostId);
        return "/post/updatePostForm";
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다")
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostDto postDto) {
        postService.updatePost(postId, postDto);
        return "redirect:/post ";
    }

    //delete
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제합니다")
    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/post";
    }

    @Operation(summary = "게시물 좋아요", description = "게시물에 좋아요를 누른다")
    @GetMapping("{postId}/like")
    public String likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return "redirect:/post/{postId}";
    }
}
