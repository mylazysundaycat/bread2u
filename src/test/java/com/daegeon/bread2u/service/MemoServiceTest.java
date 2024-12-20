package com.daegeon.bread2u.service;


import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.entity.Platform;
import com.daegeon.bread2u.module.memo.dto.MemoRequest;
import com.daegeon.bread2u.module.memo.dto.MemoResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MemoServiceTest extends ServiceTest {

    @Test
    @DisplayName("메모 생성 테스트")
    void create() {
        //given
//        Member member = new Member("Lazycat@test.com", "게으른고양이", null, Platform.KAKAO);
//        Bakery bakery = new Bakery(1L, "빵집 그린티", "대전광역시 유성구 봉명동", "대전광역시 봉명로",
//                "12345", 1.1, 2.2, "2024-12-30");
//        MemoRequest memoRequest = new MemoRequest("2024-12-20 빵도장깨기 완료", bakery.getId());
//
//        //when
//        memberService.createMember(member);
//        MemoResponse memoResponse = memoService.createMemo(memoRequest, member.getEmail());
//
//        //then
//        assertThat(memoResponse)
//                .extracting("content","bakeryId")
//                .containsExactly(memoRequest.getContent(), bakery.getId());
    }
}
