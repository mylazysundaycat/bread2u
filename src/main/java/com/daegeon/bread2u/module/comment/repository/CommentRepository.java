package com.daegeon.bread2u.module.comment.repository;


import com.daegeon.bread2u.module.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBreadId(Long breadId);
}
