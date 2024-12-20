package com.daegeon.bread2u.module.scrap.dto;


import com.daegeon.bread2u.module.scrap.entity.Scrap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapResponse {
    private Long id;
    private Long bakeryId;
    private Long MemberId;

    public ScrapResponse(Long id, Long bakeryId, Long memberId) {
        this.id = id;
        this.bakeryId = bakeryId;
        MemberId = memberId;
    }

    public static ScrapResponse from (Scrap scrap) {
        return new ScrapResponse(
                scrap.getId(),
                scrap.getBakery().getId(),
                scrap.getMember().getId()
        );
    }
}
