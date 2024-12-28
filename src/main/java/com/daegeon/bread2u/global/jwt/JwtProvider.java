package com.daegeon.bread2u.global.jwt;

import com.daegeon.bread2u.global.exception.BaseException;
import com.daegeon.bread2u.global.exception.ErrorCode;
import com.daegeon.bread2u.global.exception.TokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.token.expiration-time}")
    private long expirationTime;
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String CreateToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public Claims validateAvailableToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.INVALID_TOKEN);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        }
    }

    public String getSubject(String token) {
        return validateAvailableToken(token).getSubject();
    }

}
