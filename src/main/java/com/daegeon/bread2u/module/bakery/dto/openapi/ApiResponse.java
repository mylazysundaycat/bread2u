package com.daegeon.bread2u.module.bakery.dto.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse{
    private Long sn;              // 순번
    private String idsty_nm;      // 업종명
    private String bssh_nm;       // 업소명
    private String lnm_adrs;      // 지번주소
    private String rn_adrs;       // 도로명주소
    private String telno;         // 전화번호
    private Double la;            // 위도
    private Double lo;            // 경도
    private String data_stdr_de;  // 데이터 기준일자
}