package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.enums.AdminUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminUserController {


    @GetMapping("/super_admin")
    public String superAdmin() {
        return getViewForRole(AdminUserRole.SUPER_AD);
    }

    @GetMapping("/member_admin")
    public String memberAdmin() {
        return getViewForRole(AdminUserRole.MEMBER_AD);
    }

    @GetMapping("/product_admin")
    public String productAdmin() {
        return getViewForRole(AdminUserRole.PRODUCT_AD);
    }

    @GetMapping("/order_admin")
    public String orderAdmin() {
        return getViewForRole(AdminUserRole.ORDER_AD);
    }


    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
    @GetMapping(value = "/login")
    public String loginForm(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {
        if (error) {
            model.addAttribute("loginFailure", true);
            model.addAttribute("loginFailureMessage", "아이디와 패스워드를 확인하시고 다시 로그인해주세요.");
        }
        return "/login";
    }

    private String getViewForRole(AdminUserRole role) {
        return role.getKey().toLowerCase();
    }
}