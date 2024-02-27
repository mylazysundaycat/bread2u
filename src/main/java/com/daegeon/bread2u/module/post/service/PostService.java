package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //create
    public void createPost(Post post, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\daegeon\\bread2u\\lib\\upload";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"_"+file.getOriginalFilename();
        File saveFile = new File(projectPath, "fileName");
        file.transferTo(saveFile);

        post.setFilename(fileName);
        post.setFilepath(projectPath+"\\"+fileName);


        postRepository.save(post);
    }

    //read
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    //TODO 검색결과

    //TODO readByCategoryId

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
