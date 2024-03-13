package com.amzmall.project.cognito.controller;

import com.amzmall.project.cognito.dto.LogInRequestDto;
import com.amzmall.project.cognito.service.CognitoService;
import com.amzmall.project.config.jwt.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CognitoService cognitoService;

    @Autowired
    private JwtUtil jwtUtil;

    // AWS Cognito를 사용하여 회원가입을 처리하는 엔드포인트
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody LogInRequestDto loginRequestDto) {
        String signUpResult = String.valueOf(cognitoService.signUp(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        return ResponseEntity.ok(signUpResult); // 성공적으로 회원가입이 처리되면 'OK' 응답을 반환
    }

    // AWS Cognito를 사용하여 로그인을 처리하는 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequestDto loginRequestDto) {
        String token = cognitoService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token); // 성공적으로 로그인되면 토큰을 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); // 자격 증명이 유효하지 않으면 'UNAUTHORIZED' 응답을 반환
        }
    }


    // AWS Cognito를 사용하여 로그아웃을 처리하는 엔드포인트
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Token token) {
        cognitoService.logout(token.token); // 로그아웃 요청을 처리
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다."); // 로그아웃이 성공하면 메시지를 반환
    }

    // 토큰의 유효성을 검사하는 엔드포인트
    @PostMapping("/check")
    public ResponseEntity<?> checkTokenValidity(@RequestBody Token token) {
        System.out.println("token" + token.token);
        boolean isTokenValid = jwtUtil.validateToken(token.getToken());
        if (isTokenValid) {
            return ResponseEntity.ok("토큰이 유효합니다 ");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @Data
    static class Token {
        private String token;
    }
}