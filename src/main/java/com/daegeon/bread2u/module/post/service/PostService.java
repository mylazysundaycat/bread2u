package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //create
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    //read
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    //TODO 검색결과

    //TODO readByCategoryId

    //readAll
    public List<Post> readPostAll(){
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
        Optional.of(post.getCreatedAt())
                .ifPresent(at -> findPost.setModifiedAt(at));
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
