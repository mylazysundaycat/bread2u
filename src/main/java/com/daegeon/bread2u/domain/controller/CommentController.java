package com.daegeon.bread2u.domain.controller;


import com.daegeon.bread2u.domain.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
