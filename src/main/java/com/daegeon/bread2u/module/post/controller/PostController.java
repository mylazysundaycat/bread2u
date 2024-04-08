package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.comment.service.CommentService;
import com.daegeon.bread2u.module.member.entity.UserDetailsImpl;
import com.daegeon.bread2u.module.member.service.LoginService;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.entity.PostDto;
import com.daegeon.bread2u.module.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "Post API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LoginService loginService;

    @Operation(summary = "전체 글 조회", description = "Pagination을 이용하여 전체 글 목록을 조회합니다.")
    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<Post> posts = postService.findAllPost(pageable);
        model.addAttribute("posts", posts);
        loginService.addLoginMember(userDetails, model);
        return "/post/postList";
    }

    @Operation(summary = "특정 글 조회", description = "postId 값에 해당하는 게시물을 조회합니다")
    @GetMapping("/{postId}")
    public String findById(@PathVariable Long postId, Model model,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post postFindById = postService.findById(postId).orElseThrow();
        List<Comment> commentFindedByPostId = commentService.findByPostId(postId);
        model.addAttribute("post", postFindById);
        model.addAttribute("comments", commentFindedByPostId);
        loginService.addLoginMember(userDetails, model);
        return "/post/postDetail";
    }

    @Operation(summary = "게시물 작성 폼", description = "게시물 작성 폼으로 이동합니다")
    @GetMapping("/create")
    public String createPost(Model model,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("post", new PostDto());
        loginService.addLoginMember(userDetails, model);
        return "/post/createPostForm";
    }

    @Operation(summary = "게시물 작성", description = "게시물을 작성합니다")
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDto postDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             Model model) {
        postService.createPost(postDto);
        loginService.addLoginMember(userDetails, model);
        return "redirect:/post";
    }

    @Operation(summary = "게시물 수정 폼", description = "게시물 수정 폼으로 이동합니다")
    @GetMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, Model model,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post findByPostId = postService.findById(postId)
                .orElseThrow();
        model.addAttribute("post", findByPostId);
        loginService.addLoginMember(userDetails, model);
        return "/post/updatePostForm";
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다")
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostDto postDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             Model model) {
        postService.updatePost(postId, postDto);
        loginService.addLoginMember(userDetails, model);
        return "redirect:/post ";
    }

    //delete
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제합니다")
    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             Model model) {
        postService.deletePost(postId);
        loginService.addLoginMember(userDetails, model);
        return "redirect:/post";
    }

    @Operation(summary = "게시물 좋아요", description = "게시물에 좋아요를 누른다")
    @GetMapping("{postId}/like")
    public String likePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) {
        postService.likePost(postId);
        loginService.addLoginMember(userDetails, model);
        return "redirect:/post/{postId}";
    }
}
