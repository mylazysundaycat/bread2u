package com.daegeon.bread2u.global.config;




import com.daegeon.bread2u.global.jwt.JwtSecurityConfig;
import com.daegeon.bread2u.global.jwt.TokenProvider;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    public SecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                //로그인 페이지
                .formLogin(login -> login
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login-process")
                        .usernameParameter("memberName")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/index", true) //로그인 성공시 이동할 url
                        .permitAll()
                )
                //url 인가 처리
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers(PathRequest
                                .toStaticResources()
                                .atCommonLocations()).permitAll()//정적자원
                        .requestMatchers("/member"
                                ,"/member/login"
                                , "/bread/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // enable h2-console
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .with(new JwtSecurityConfig(tokenProvider), customizer -> {
                });
        return http.build();
    }
}