package com.daegeon.bread2u.module.memo.dto;


import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.member.entity.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemoRequest {
    private String content;
    private Long bakeryId;
    public MemoRequest(String content, Long bakeryId) {
        this.content = content;
        this.bakeryId = bakeryId;
    }
}
