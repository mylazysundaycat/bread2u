package com.daegeon.bread2u.module.memo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemoRequest {
    @NotBlank(message = "한 줄 메모에 내용을 입력해주세요.")
    private String content;
    private Long bakeryId;
    public MemoRequest(String content, Long bakeryId) {
        this.content = content;
        this.bakeryId = bakeryId;
    }
}
