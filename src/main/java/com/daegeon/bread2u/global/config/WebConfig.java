package com.daegeon.bread2u.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Value("${origin.url}")
    private String url;
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    public static final String ALLOWED_HEADERS = "DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(url)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedHeaders(ALLOWED_HEADERS.split(","))
                .allowCredentials(true);
    }
}