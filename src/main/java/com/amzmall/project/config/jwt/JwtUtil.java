package com.amzmall.project.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static Key key; // Cognito 공개 키

    @Value("https://cognito-idp.ap-northeast-2.amazonaws.com/ap-northeast-2_qD0HzA3fK/.well-known/jwks.json")
    private String publicKeyPem; // application.properties에 정의된 Cognito 공개 키

    @PostConstruct
    public void init() {
        // PEM 포맷의 공개 키를 Key 객체로 변환하는 로직
        // 이 부분은 Cognito의 실제 공개 키로 초기화되어야 함
        // String publicKeyPem = "-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----";
        this.key = Keys.hmacShaKeyFor(publicKeyPem.getBytes());
    }

    // 토큰에서 모든 클레임을 추출하는 메서드
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 특정 클레임을 추출하는 메서드 (예: 사용자 이름)
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    // 토큰의 만료 시간을 추출하는 메서드
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    // 토큰의 유효성 검증 메서드
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.error("Token validation error: {}", e.getMessage());
        }
        return false;
    }
}
