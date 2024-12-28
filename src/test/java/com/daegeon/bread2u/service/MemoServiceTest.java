package com.daegeon.bread2u.service;


import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Platform;
import com.daegeon.bread2u.module.memo.dto.MemoRequest;
import com.daegeon.bread2u.module.memo.dto.MemoResponse;
import com.daegeon.bread2u.module.memo.entity.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MemoServiceTest extends ServiceTest {
    private final Member member = new Member("lazycat@test.com","게으른냥", "imagesrc", Platform.KAKAO);
    private final Bakery bakery = new Bakery("따끈따끈베이커리", "투니버스 27번","투니버스로 27길",
            "01012345678", 1.1d, 1.1d, "2024-12-25");
    private Bakery savedBakery;
    @BeforeEach
    void setUp() {
        memberRepository.save(member);
        savedBakery = bakeryRepository.save(bakery);
    }
    @Test
    void creatMemo() {
        //given
        String email = member.getEmail();
        MemoRequest memoRequest = new MemoRequest("태양의 손을 가진 제빵사가 있는 곳입니당", 1l);
        System.out.println(savedBakery.getId());

        //when
        MemoResponse response = memoService.createMemo(memoRequest, email);

        // then
        assertNotNull(response);
        assertEquals(memoRequest.getContent(), response.getContent());
        assertEquals(savedBakery.getId(), response.getBakeryId());

        List<Memo> savedMemos = memoRepository.findAll();
        assertEquals(1, savedMemos.size());
        Memo savedMemo = savedMemos.get(0);
        assertEquals(memoRequest.getContent(), savedMemo.getContent());

    }

}
