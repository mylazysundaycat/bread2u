package com.daegeon.bread2u.domain.repository;


import com.daegeon.bread2u.domain.entity.Bread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 빵 포스트 리포지토리
 */
@Repository
public interface BreadRepository extends JpaRepository<Bread, Long> {
    public Page<Bread> findAll(Pageable pageable);
    public Bread getReferenceById(Long braed_id);
    public List<Bread> findAllById(Long Shop_id);
    /**
     * Returns a bread list by shop
     */

}
