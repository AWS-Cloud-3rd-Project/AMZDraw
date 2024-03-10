package com.amzmall.project.config.jwt;


import com.amzmall.project.cognito.service.CognitoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private CognitoService cognitoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            // 토큰이 유효한지 CognitoService를 통해 검증
            if (JwtUtil.validateToken(jwt)) {
                // 토큰 검증 성공 시, 추가적인 인증 처리가 필요한 경우 여기에 로직을 추가
                // 예: 사용자의 세부 정보를 로드하고 SecurityContextHolder에 설정
            } else {
                // 토큰이 유효하지 않은 경우, 필요에 따라 처리 (예: 오류 로깅, 응답 설정 등)
            }
        }

        chain.doFilter(request, response); // 다음 필터로 요청 및 응답을 전달
    }
}

