package com.daegeon.bread2u.domain.repository.dto;


import com.daegeon.bread2u.domain.entity.Comment;
import com.daegeon.bread2u.domain.entity.Member;
import com.daegeon.bread2u.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Builder
@Transactional
public class BreadDto {

    private Long id;

    private String title;

    private Long price;

    private String detail;

    private Shop shop;

    private Member member;

    private List<Comment> comments;

}
