package com.daegeon.bread2u.domain.service;


import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.repository.BreadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BreadService {

    private final BreadRepository breadRepository;
    
    //빵 전체 조회
    public Page<Bread> findAll(Pageable pageable) {
        return breadRepository.findAll(pageable);
    }
    
    //Read
    //TODO Exceoption 추가
    public Bread findById(Long bread_id) {
        return breadRepository.findById(bread_id)
                .orElseThrow(
                );
    }
}
 