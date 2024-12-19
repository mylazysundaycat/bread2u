package com.daegeon.bread2u.module.scrap.controller;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import com.daegeon.bread2u.module.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScrapController {
    private final ScrapService scrapService;

    @GetMapping("/scrap/{bakeryId}/{email}")
    public ResponseEntity<Void> createScrap(@PathVariable String email, @PathVariable Long bakeryId) {
        scrapService.createScrap(email, bakeryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/scrap/{bakeryId}/{email}")
    public ResponseEntity<Void> deleteScrap(@PathVariable String email, @PathVariable Long bakeryId) {
        scrapService.deleteScrap(email, bakeryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
