package com.daegeon.bread2u.domain.repository.dto;


import com.daegeon.bread2u.domain.entity.Comment;
import com.daegeon.bread2u.domain.entity.Member;
import com.daegeon.bread2u.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
public class BreadDto {
    private String title;
    private Long price;
    private String detail;
}
