package com.daegeon.bread2u.module.memo.service;

import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.bakery.service.BakeryService;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.service.MemberService;
import com.daegeon.bread2u.module.memo.dto.MemoRequest;
import com.daegeon.bread2u.module.memo.dto.MemoResponse;
import com.daegeon.bread2u.module.memo.entity.Memo;
import com.daegeon.bread2u.module.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final MemberService memberService;
    private final BakeryService bakeryService;

    public MemoResponse createMemo(MemoRequest memoRequest, String email) {
        Member member = memberService.getMemberByEmail(email);
        Bakery bakery = bakeryService.getBakery(memoRequest.getBakeryId());
        Memo memo = new Memo(memoRequest.getContent(), member, bakery);
        return MemoResponse.from(memoRepository.save(memo));
    }

    public MemoResponse setMemo(MemoRequest memoRequest, String email) {
        Member member = memberService.getMemberByEmail(email);
        Bakery bakery = bakeryService.getBakery(memoRequest.getBakeryId());

        Memo memo = memoRepository.findByBakeryIdAndMemberId(bakery.getId(), member.getId())
                .orElseGet(() -> new Memo(memoRequest.getContent(), member, bakery));

        memo.updateContent(memoRequest.getContent());

        return MemoResponse.from(memo);
    }


    public List<Memo> getMemberMemo(String email) {
        return memoRepository.findAllByMemberEmail(email);
    }

}
