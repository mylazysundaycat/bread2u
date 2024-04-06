package com.daegeon.bread2u.module.member.repository;

import com.daegeon.bread2u.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}

