package com.daegeon.bread2u.module.member.repository;

import com.daegeon.bread2u.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
