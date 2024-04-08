package com.daegeon.bread2u.global.jwt;

import com.daegeon.bread2u.global.redis.RedisService;
import com.daegeon.bread2u.module.member.entity.Role;
import com.daegeon.bread2u.module.member.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer=";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    private final RedisService redisService;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기 -> 헤더검사 없으면 쿠키검사
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        /*헤더에 값이 없다면 토큰 확인*/
        if (bearerToken == null) {
            Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
            if (cookies != null) {
                for (Cookie c : cookies) {
                    String name = c.getName(); // 쿠키 이름 가져오기

                    String value = c.getValue(); // 쿠키 값 가져오기
                    if (name.equals(AUTHORIZATION_HEADER)) {
                        bearerToken = value;
                    }
                }
            }
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Access Token 발급
     */
    public String createAccessToken(Authentication authentication, Long memberId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime()+accessExpirationTime);
        return BEARER_PREFIX + Jwts.builder()
                        .setSubject(authentication.getName())
                        .claim("memberId", memberId)
                        .setIssuedAt(now)
                        .setExpiration(expireDate)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * Refresh Token 발급
     */
    public String createRefreshToken (Authentication authentication, Long memberId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName()) // Token 이름은 username
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(key, signatureAlgorithm)
                .compact();
        redisService.setValues(authentication.getName(), refreshToken, Duration.ofMillis(refreshExpirationTime));
        return refreshToken;

    }

    public Cookie createCookie(String refreshToken) {
        String cookieName = "refreshToken";
        Cookie cookie = new Cookie(cookieName, refreshToken);

        cookie.setHttpOnly(true); //보안강화, 자바스크립트에 대한 접근 차단
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(12 * 60 * 60 * 24); // accessToken 유효

        return cookie;
    }

    // Token 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    /**
     * Token으로부터 Claim(정보) 획득
     * @param token
     * @return
     */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * token으로부터 Authenication을 찾는다
     * @param token
     * @return 
     */
    public Authentication getAuthentication(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody() //Claim 가져옴
                .getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject); //User 객체 생성
        //Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}