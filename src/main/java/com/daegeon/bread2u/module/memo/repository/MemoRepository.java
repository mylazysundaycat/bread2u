package com.daegeon.bread2u.module.memo.repository;

import com.daegeon.bread2u.module.memo.entity.Memo;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    public List<Memo> findAllByMemberEmail(String memberEmail);
    public Optional<Memo> findByBakeryIdAndMemberId(Long bakeryId, Long memberId);

}
