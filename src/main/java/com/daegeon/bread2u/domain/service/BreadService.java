package com.daegeon.bread2u.domain.service;


import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.repository.BreadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreadService {

    private final BreadRepository breadRepository;
    
    //빵 전체 조회
//    public Page<Bread> findAll(Pageable pageable) {
//        return breadRepository.findAll(pageable);
//    }
    public List<Bread> findAll(){
        return breadRepository.findAll();
    }
    
    //빵 조회
    //TODO Exceoption
    public Bread findById(Long bread_id) {
        return breadRepository.findById(bread_id)
                .orElseThrow();
    }

    //빵 작성
    public Bread createBread(Bread bread){
        Bread createdBread = breadRepository.save(bread);
        return createdBread;
    }

    //빵 수정
    //TODO Exceoption
    //TODO Builder도 사용해보기
    public Bread updateBread(Long breadId, Bread bread) {
        Bread findBread = breadRepository.findById(breadId) //영속성 엔티티 찾아오기
                .orElseThrow();
        Optional.ofNullable(bread.getTitle())
                .ifPresent(title -> findBread.setTitle(title));
        Optional.ofNullable(bread.getDetail())
                .ifPresent(detail -> findBread.setDetail(detail));
        return breadRepository.save(findBread);
    }

    //빵 삭제
    public void deleteBread(Bread bread) {
        Bread findBread = breadRepository.findById(bread.getId())
                .orElseThrow();
        breadRepository.delete(findBread);
    }
}
 