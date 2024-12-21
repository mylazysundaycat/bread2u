package com.daegeon.bread2u.module.bakery.controller;


import com.daegeon.bread2u.module.bakery.dto.BakeryResponse;
import com.daegeon.bread2u.module.bakery.service.BakeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BakeryController {

    private final BakeryService bakeryService;

    @GetMapping("/bread")
    public ResponseEntity<List<BakeryResponse>> getBreadList() {
        List<BakeryResponse> bakeryRespons = bakeryService.getBreadList();
        return new ResponseEntity<>(bakeryRespons, HttpStatus.OK);
    }

//    @GetMapping("/bread/update")
//    public ResponseEntity<Void> updateOpenBreadApi() {
//        bakeryService.saveOpenBreadApi();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
