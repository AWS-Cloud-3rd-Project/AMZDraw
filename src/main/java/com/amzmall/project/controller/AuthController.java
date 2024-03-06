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

    @PostMapping(value = "/sign-up")
    @ResponseBody
    public String signUp(@RequestBody LogInRequestDto loginRequestDto){
        cognitoService.signUp(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return "sign-up";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody LogInRequestDto loginRequestDto){
        return cognitoService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public String logout(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }

    @PostMapping(value = "/check")
    @ResponseBody
    public String check(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }
}
