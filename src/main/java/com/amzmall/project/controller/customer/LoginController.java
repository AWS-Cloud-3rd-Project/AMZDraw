package com.amzmall.project.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    @GetMapping(value = "/customer/login")
    public String loginForm(HttpSession session, Model model) {
        if (isUserLoggedIn()) {
            return "redirect:/"; // 이미 로그인한 경우 홈 페이지로 리다이렉트
        }

        handleLoginFailure(session, model);

        return "/customer/login";
    }

    private boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    private void handleLoginFailure(HttpSession session, Model model) {
        Object loginException = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (loginException != null) {
            model.addAttribute("loginFailure", true);
            model.addAttribute("loginFailureMessage", "아이디와 패스워드를 확인하시고 다시 로그인해주세요.");
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION"); // 에러 메시지를 한 번 읽으면 세션에서 제거
        }
    }

    @PostMapping("/customer/logout")
    public String logout(HttpSession session) {
        // 현재 세션을 무효화
        if (session != null) {
            session.invalidate();
        }

        // 로그아웃 후에는 로그인 페이지로 리다이렉트
        return "redirect:/customer/login";
    }
}
