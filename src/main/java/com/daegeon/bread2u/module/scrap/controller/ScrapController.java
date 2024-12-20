package com.daegeon.bread2u.module.scrap.controller;

import com.daegeon.bread2u.global.auth.Auth;
import com.daegeon.bread2u.module.member.dto.LoginMemberRequest;
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
    @GetMapping("/scrap/{bakeryId}")
    public ResponseEntity<Void> createScrap(@Auth LoginMemberRequest loginMemberRequest,
                                            @PathVariable Long bakeryId) {
        scrapService.createScrap(loginMemberRequest.getEmail(), bakeryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/scrap/{bakeryId}")
    public ResponseEntity<Void> deleteScrap(@Auth LoginMemberRequest loginMemberRequest,
                                            @PathVariable Long bakeryId) {
        scrapService.deleteScrap(loginMemberRequest.getEmail(), bakeryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
