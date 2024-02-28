package com.amzmall.project.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    private static final String LOGIN_FORM_PAGE = "loginForm";

    @GetMapping("/")
    public String loginForm() {
        return LOGIN_FORM_PAGE;
    }
}
