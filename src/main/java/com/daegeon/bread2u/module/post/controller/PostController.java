package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.dto.PostDto;
import com.daegeon.bread2u.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //create
    @GetMapping("/post/create")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        return "/post/createPostForm";
    }
    @PostMapping("/post/create")
    public String createPost(@ModelAttribute Post post, MultipartFile file) throws IOException {
        postService.createPost(post, file);
        return "redirect:/post";
    }

    //read
    //TODO
    @GetMapping("/post/{postId}")
    public String findById(@PathVariable Long postId, Model model) {
        Post postFindedById = postService.findById(postId)
                .orElseThrow();
        model.addAttribute("postFindedById",postFindedById);
        return "/post/postDetail";
    }
    //readAll
    @GetMapping("/post")
    public String findAll(Model model){
        model.addAttribute("posts", postService.findAll() );
        return "/post/postList";
    }

    //update
    @PostMapping("/post/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post post){
        postService.updatePost(postId, post);
        return "redirect:/post/{postId}";
    }

    //delete
    @GetMapping("/post/{postid}/delete")
    public String deletePost(@PathVariable Long postid) {
        postService.deletePost(postid);
        return "redirect:/post";
    }
}
