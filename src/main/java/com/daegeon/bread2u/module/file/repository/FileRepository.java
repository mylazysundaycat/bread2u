package com.daegeon.bread2u.module.file.repository;

import com.daegeon.bread2u.module.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    public Optional<File> findByPostId(Long postId);
}
