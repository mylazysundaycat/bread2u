package com.daegeon.bread2u.module.post.controller;


import com.daegeon.bread2u.module.post.dto.BreadResponse;
import com.daegeon.bread2u.module.post.service.BreadService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BreadController {

    private final BreadService breadService;

    @GetMapping("/bread")
    public ResponseEntity<List<BreadResponse>> getBreadList() {
        List<BreadResponse> breadResponses = breadService.getBreadList();
        return new ResponseEntity<>(breadResponses, HttpStatus.OK);
    }

    @GetMapping("/bread/update")
    public ResponseEntity<Void> updateOpenBreadApi() {
        breadService.saveOpenBreadApi();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
