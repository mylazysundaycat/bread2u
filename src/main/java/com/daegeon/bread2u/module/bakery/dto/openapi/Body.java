package com.daegeon.bread2u.module.bakery.dto.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Body {
    private int totalCnt;              // 총 개수
    private List<ApiResponse> items;   // items 리스트
}
