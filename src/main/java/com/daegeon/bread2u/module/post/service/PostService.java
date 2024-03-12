package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.file.entity.File;
import com.daegeon.bread2u.module.file.service.FileService;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberDto;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.entity.PostDto;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final MemberService memberService; //추가!

    //create
    public void createPost(PostDto postDto, MemberDto memberDto) {
        File saveFile = fileService.createFile(postDto.getFile());
        Member member = memberService.findByMembername(memberDto.getMembername()).orElseThrow(); //추가!
        List<Comment> comments = new ArrayList<>();
        Post savePost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .file(saveFile)
                .likes(0L)
                .view(0L)
                .comment(comments)
                .member(member)
                .build();
        postRepository.save(savePost);
    }

    //update
    //TODO EXCEPTION
    public Post updatePost(Long postId, PostDto postDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        findPost.setTitle(postDto.getTitle());
        findPost.setContent(postDto.getContent());
        File saveFile = fileService.createFile(postDto.getFile());
        findPost.setFile(saveFile);
//        Optional.ofNullable(post.getComment())
//                .ifPresent(comment-> findPost.setComment(comment));
//        Optional.of(post.getTitle())
//                .ifPresent(title->findPost.setTitle(title));
//        Optional.of(post.getCreatedAt())
//                .ifPresent(at -> findPost.setModifiedAt(at));
        return postRepository.save(findPost);
    }

    //read
    public Optional<Post> findById(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        findPost.setView(findPost.getView()+1);
        return postRepository.findById(postId);
    }

    //readAll
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    //TODO EXCEPTION
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);

    }

    public Post likePost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        findPost.setLikes(findPost.getLikes()+1);
        return postRepository.save(findPost);
    }
}
