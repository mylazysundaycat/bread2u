package com.daegeon.bread2u.domain.controller;

import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.service.BreadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class BreadController {
    private final BreadService breadService;


    @Operation(summary = "특정 빵 조회", description = "id에 해당하는 빵을 보여준다")
    public Bread findById(Long bread_id) {
        return breadService.findById(bread_id);
    }

//    @Operation(summary = "빵 제작", description = "빵 게시물을 생성한다")
//    public Bread
}
