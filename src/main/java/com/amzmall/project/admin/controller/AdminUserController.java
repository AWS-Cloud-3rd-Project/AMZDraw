package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.user.AdminUser;
import com.amzmall.project.admin.service.AdminUserDetailService;
import com.amzmall.project.admin.service.dto.AdminUserFormDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AdminUserController {


    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/superadmin")
    public String superadmin(){
        return "superadmin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
    @GetMapping(value = "/admin/login")
    public String loginForm(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {
        if (error) {
            model.addAttribute("loginFailure", true);
            model.addAttribute("loginFailureMessage", "아이디와 패스워드를 확인하시고 다시 로그인해주세요.");
        }
        return "/admin/login";
    }
}