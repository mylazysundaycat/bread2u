package com.daegeon.bread2u.module.member.repository;

import com.daegeon.bread2u.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findAll();
}
