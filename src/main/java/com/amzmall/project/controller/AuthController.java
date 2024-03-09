package com.amzmall.project.controller;

import com.amzmall.project.dto.LogInRequestDto;
import com.amzmall.project.service.CognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CognitoService cognitoService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody LogInRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        cognitoService.signUp(username, password);
        return "sign-up";
    }

    @PostMapping("/login")
    //코그니토 서비스 사용해서 로그인 수행하고 액세스 토큰 반환
    public String login(@RequestBody LogInRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        return cognitoService.login(username, password);
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    //코그니토 서비스를 사용해서 전달된 액세스 토큰 사용해서 로그아웃 수행
    public String logout(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }

    //세션 확인 요청 처리 - 전달된 액세스 토큰 사용하여 세션 확인 수행
    @PostMapping(value = "/check")
    @ResponseBody
    public String check(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }
}
