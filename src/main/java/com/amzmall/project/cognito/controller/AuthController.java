package com.amzmall.project.cognito.controller;

import com.amzmall.project.cognito.dto.LoginReqDto;
import com.amzmall.project.cognito.service.CognitoService;
import com.amzmall.project.cognito.config.jwt.JwtUtil;
import com.amzmall.project.util.dto.CommonResult;
import com.amzmall.project.util.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private final CognitoService cognitoService;
    private final ResponseService responseService;
    private final int FAIL = -1;

    @Autowired
    private JwtUtil jwtUtil;

    // AWS Cognito를 사용하여 회원가입을 처리하는 엔드포인트
    @PostMapping("/sign-up")
    @Operation(summary="회원가입 요청", description="이메일, 비밀번호를 이용하여 회원가입합니다.")
    public CommonResult signUp(@RequestBody LoginReqDto loginReqDto) {
        try {
            cognitoService.signUp(loginReqDto);
            return responseService.getSuccessResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return responseService.getFailResult(FAIL, e.getMessage());
        }
    }

    // AWS Cognito를 사용하여 로그인을 처리하는 엔드포인트
    @PostMapping("/login")
    @Operation(summary="로그인", description="이메일, 비밀번호를 이용하여 로그인합니다.")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        String token = cognitoService.login(loginReqDto.getEmail(), loginReqDto.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token); // 성공적으로 로그인되면 토큰을 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); // 자격 증명이 유효하지 않으면 'UNAUTHORIZED' 응답을 반환
        }
    }


    // AWS Cognito를 사용하여 로그아웃을 처리하는 엔드포인트
    @PostMapping("/logout")
    @Operation(summary="로그아웃", description="로그아웃합니다.")
    public ResponseEntity<?> logout(@RequestBody Token token) {
        cognitoService.logout(token.token); // 로그아웃 요청을 처리
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다."); // 로그아웃이 성공하면 메시지를 반환
    }

    // 토큰의 유효성을 검사하는 엔드포인트
    @PostMapping("/check")
    @Operation(summary="토큰 유효성 검사", description="토큰 유효성 검사합니다.")
    public ResponseEntity<?> checkTokenValidity(@RequestBody Token token) {
        log.info("token" + token.token);
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