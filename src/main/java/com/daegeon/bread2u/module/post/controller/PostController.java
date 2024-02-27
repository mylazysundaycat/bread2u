package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.dto.PostDto;
import com.daegeon.bread2u.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String createPost(@ModelAttribute Post post) {
        postService.createPost(post);
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

    //update
    @PostMapping("/pots/{postid}")
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
