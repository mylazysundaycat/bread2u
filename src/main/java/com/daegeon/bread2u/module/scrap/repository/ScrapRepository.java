package com.daegeon.bread2u.module.scrap.repository;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.post.entity.Bakery;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    public Scrap findByBakeryAndMember(Bakery bakery, Member member);
    public List<Scrap> findAllByMemberEmail(String memberEmail);
}
