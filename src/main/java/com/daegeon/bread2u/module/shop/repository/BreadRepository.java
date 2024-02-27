package com.daegeon.bread2u.module.shop.repository;


import com.daegeon.bread2u.module.shop.entity.Bread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 빵 포스트 리포지토리
 */
@Repository
public interface BreadRepository extends JpaRepository<Bread, Long> {
    //public Page<Bread> findAll(Pageable pageable);
    public Optional<Bread> findById(Long braed_id);



//    public List<Bread> findAllShopIdByCreatedAtDesc(Shop shop, Pageable pageable);
//    /**
//     * Returns a bread list by shop
//     */

}
