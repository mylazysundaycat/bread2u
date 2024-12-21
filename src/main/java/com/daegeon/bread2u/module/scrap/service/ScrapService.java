package com.daegeon.bread2u.module.scrap.service;


import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.bakery.service.BakeryService;
import com.daegeon.bread2u.module.scrap.entity.Scrap;
import com.daegeon.bread2u.module.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final BakeryService bakeryService;
    private final MemberService memberService;

    public void createScrap(String email, Long bakeryId) {
        Bakery bakery = bakeryService.getBakery(bakeryId);
        Member member = memberService.getMemberByEmail(email);
        Scrap scrap = new Scrap(bakery, member);
        scrapRepository.save(scrap);
    }

    public void deleteScrap(String email, Long bakeryId) {
        Bakery bakery = bakeryService.getBakery(bakeryId);
        Member member = memberService.getMemberByEmail(email);
        Scrap scrap = scrapRepository.findByBakeryAndMember(bakery, member);
        scrapRepository.delete(scrap);
    }

    public List<Scrap> getMemberScraps(String email) {
        return scrapRepository.findAllByMemberEmail(email);
    }

}
