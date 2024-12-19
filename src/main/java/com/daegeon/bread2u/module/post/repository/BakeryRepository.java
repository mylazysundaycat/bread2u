package com.daegeon.bread2u.module.post.repository;


import com.daegeon.bread2u.module.post.entity.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Long> {
}
