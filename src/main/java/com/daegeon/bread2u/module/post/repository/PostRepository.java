package com.daegeon.bread2u.module.post.repository;

import com.daegeon.bread2u.module.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    
    /*
    // Pageable 추가
    // JPQL을 이용하여 DTO에 한번에 담기
    @Query("select new com.daegeon.bread2u.module.post.entity.PostDto(p.title, p.content) from Post p")
    Page<PostDto> findAllPostDto(Pageable pageable);
     */
    //Pageable 추가
    Page<Post> findAll(Pageable pageable);
}
