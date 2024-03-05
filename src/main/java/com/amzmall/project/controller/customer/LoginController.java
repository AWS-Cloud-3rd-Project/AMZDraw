package com.amzmall.project.controller.customer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping(value = "/customer/login")
    public String loginForm(HttpSession session, Model model) {
        // 이미 로그인한 사용자인 경우 로그인 페이지 접근을 막을 수 있음
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/"; // 이미 로그인한 경우 홈 페이지로 리다이렉트
        }

        // 로그인 에러 메시지 확인
        if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {
            model.addAttribute("loginFailure", true);
            model.addAttribute("loginFailureMessage", "아이디와 패스워드를 확인하시고 다시 로그인해주세요.");
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION"); // 에러 메시지를 한 번 읽으면 세션에서 제거
        }

        return "/customer/login";
    }
}

