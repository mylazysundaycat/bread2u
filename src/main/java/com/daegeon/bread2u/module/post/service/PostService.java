package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.file.entity.File;
import com.daegeon.bread2u.module.file.service.FileService;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.entity.PostDto;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    //create
    public void createPost(PostDto postDto) {
        File saveFile = fileService.createFile(postDto.getFile());
        List<Comment> comments = new ArrayList<>();
        Post savePost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .file(saveFile)
                .likes(0L)
                .view(0L)
                .comment(comments)
                .build();
        postRepository.save(savePost);
    }

    //read
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    //readAll
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    //update
    //TODO EXCEPTION
    public Post updatePost(Long postId, Post post) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        Optional.ofNullable(post.getComment())
                .ifPresent(comment-> findPost.setComment(comment));
        Optional.of(post.getTitle())
                .ifPresent(title->findPost.setTitle(title));
//        Optional.of(post.getCreatedAt())
//                .ifPresent(at -> findPost.setModifiedAt(at));
        return postRepository.save(findPost);
    }

    //delete

    //TODO EXCEPTION
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        postRepository.delete(findPost);
    }
}
