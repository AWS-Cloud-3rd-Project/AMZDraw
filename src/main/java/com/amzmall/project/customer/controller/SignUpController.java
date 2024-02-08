package com.amzmall.project.customer.controller;

//사용자가 입력한 회원가입 데이터를 db에 전달 및 처리
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @RequestMapping("/form")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup-form";
    }

    @PostMapping("/process")
    public String processSignUpForm(@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup-form"; // 유효성 검사 실패 시 다시 입력 폼으로 이동
        }

        // 여기에서 회원 가입 처리 로직을 수행 (예: 서비스를 통한 데이터베이스 저장 등)

        return "signup-success"; // 성공 페이지로 이동
    }
}
