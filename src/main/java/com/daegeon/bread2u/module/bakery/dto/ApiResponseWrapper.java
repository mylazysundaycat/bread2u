package com.daegeon.bread2u.module.bakery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
    @Setter
    public class ApiResponseWrapper {
        private Response response;
        @Getter
        @Setter
        public static class Response {
            private Body body;
        }
    }
