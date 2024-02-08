package com.amzmall.project.customer.controller;

import com.amzmall.project.customer.domain.user.User;
import com.amzmall.project.customer.provider.JwtTokenProvider;
import com.amzmall.project.customer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/?")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestParam String code) {
        // 사용자 인증
        LoginResult loginResult = authService.login(code);
        UserDetails userDetails = loadUserByUsername(loginResult.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 생성
        String jwtToken = jwtTokenProvider.generateToken(authentication);

        // 응답
        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        return ResponseEntity.ok(jwtResponse);
    }

    // 사용자 정보 로드 메서드 (사용자가 존재하는지 확인)
    private UserDetails loadUserByUsername(String username) {
        // 실제로는 사용자 정보를 데이터베이스에서 가져오는 로직을 구현
        // 여기서는 간단하게 UserService 클래스를 사용하여 UserDetails를 반환하는 것으로 가정
        User user = userService.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}

