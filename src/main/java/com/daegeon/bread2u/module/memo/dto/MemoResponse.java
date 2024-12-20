package com.daegeon.bread2u.module.memo.dto;


import com.daegeon.bread2u.module.memo.entity.Memo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemoResponse {
    private Long id;
    private String content;
    private Long bakeryId;
    private Long memberId;

    public MemoResponse(Long id, String content, Long bakeryId, Long memberId) {
        this.id = id;
        this.content = content;
        this.bakeryId = bakeryId;
        this.memberId = memberId;
    }

    public static MemoResponse from(Memo memo) {
        return new MemoResponse(
                memo.getId(), memo.getContent(), memo.getBakery().getId(), memo.getMember().getId()
        );
    }
}
