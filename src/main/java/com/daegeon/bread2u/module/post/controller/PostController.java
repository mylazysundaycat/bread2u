package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.file.service.FileService;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.entity.PostDto;
import com.daegeon.bread2u.module.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "전체 글 조회")
    @GetMapping
    public String findAll(Model model){
        model.addAttribute("posts", postService.findAll() );
        return "/post/new_postList";
    }
    //TODO
    @Operation(summary = "특정 글 조회")
    @GetMapping("/{postId}")
    public String findById(@PathVariable Long postId, Model model) {
        Post postFindedById = postService.findById(postId)
                .orElseThrow();
        model.addAttribute("post",postFindedById);
        return "/post/postDetail";
    }

    @GetMapping("/create")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        return "/post/new_createPostForm";
    }
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDto postDto){
        postService.createPost(postDto);
        return "redirect:/post";
    }

    //update
    @PostMapping("/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post post){
        postService.updatePost(postId, post);
        return "redirect:/post/{postId}";
    }

    //delete
    @GetMapping("/{postid}/delete")
    public String deletePost(@PathVariable Long postid) {
        postService.deletePost(postid);
        return "redirect:/post";
    }
}
