package com.daegeon.bread2u.module.post.repository;


import com.daegeon.bread2u.module.post.entity.Bread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreadRepository extends JpaRepository<Bread, Long> {
}
