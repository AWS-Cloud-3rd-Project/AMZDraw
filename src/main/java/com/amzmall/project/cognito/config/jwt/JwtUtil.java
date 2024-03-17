package com.amzmall.project.cognito.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static Key key; // Cognito 공개 키

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
            RSAKeyProvider keyProvider = new AwsCognitoRSAProvider();
            Algorithm algorithm = Algorithm.RSA256(keyProvider);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                .build();

            jwtVerifier.verify(token);
            return true;
        } catch (JwtException e) {
            logger.error("Token validation error: {}", e.getMessage());
        }
        return false;
    }
}