package com.daegeon.bread2u.module.comment.service;


import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.comment.entity.CommentRequestDto;
import com.daegeon.bread2u.module.comment.repository.CommentRepository;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.dto.SignUpRequestDto;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //Create
    public Comment createComment(Long postId, CommentRequestDto commentDto, SignUpRequestDto signUpRequestDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow();

        Member findMember = memberRepository.findByUsername(signUpRequestDto.getUsername())
                .orElseThrow();

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .likes(0L)
                .post(findPost)
                .member(findMember)
                .build();

        //TODO Cascade 이용해서 수정
        findPost.getComment().add(comment);
        
        return commentRepository.save(comment);
    }

    //ReadAll
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }


    //Read
    public Optional<Comment> findByCommentId(Long commentId) {
        return commentRepository.findById(commentId);
    }

    //Update
    //TODO Exception
    public Comment updateComment(Long commentId, Comment comment) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow();
        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> findComment.setContent(content));
        return commentRepository.save(findComment);
    }

    //Delete
    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow();
        commentRepository.delete(findComment);
    }
}
