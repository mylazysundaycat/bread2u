package com.daegeon.bread2u.module.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

    @Getter
    @Setter
    public class ApiResponseWrapper {
        private Response response;
        @Getter
        @Setter
        public static class Response {
            private BakeryResponse body;
        }
    }
