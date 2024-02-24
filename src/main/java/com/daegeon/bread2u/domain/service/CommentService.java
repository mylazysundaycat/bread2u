package com.daegeon.bread2u.domain.service;


import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.entity.Comment;
import com.daegeon.bread2u.domain.repository.CommentRepository;
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

    //Create
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    //ReadAll
    public List<Comment> findByBreadId(Long breadId) {
        return commentRepository.findByBreadId(breadId);
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
