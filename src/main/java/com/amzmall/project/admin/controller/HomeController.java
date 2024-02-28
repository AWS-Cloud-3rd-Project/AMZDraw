package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.user.AdminUserDetail;
import com.amzmall.project.admin.enums.AdminUserRole;
import com.amzmall.project.admin.service.AdminUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private static final String INDEX_PAGE = "index";
    private final AdminUserDetailService adminUserDetailService;
    @GetMapping(value = {"/", "/index", "/home"})
    @PreAuthorize("hasAnyRole('" + CategoryController.AdminUserRoles.MEMBER_AD + "','" +
            CategoryController.AdminUserRoles.SUPER_AD + "','" +
            CategoryController.AdminUserRoles.PRODUCT_AD + "','" +
            CategoryController.AdminUserRoles.ORDER_AD + "')")

    public String index(@AuthenticationPrincipal AdminUserDetail adminUserDetail, Model model) {

        log.info(">>> Login Admin User, {}", adminUserDetail);

        addCommonModelAttributes(model);

        model.addAttribute("dailyPaymentCnt", 1_000_000);
        model.addAttribute("dailyCancelCnt", 50);
        model.addAttribute("dailyCustomerJoinCnt", 200);
        model.addAttribute("dailyCustomerWithdrawalCnt", 30);
        model.addAttribute("dailyProductRegCnt", 5_000);

        model.addAttribute("username", adminUserDetail.getUsername());

        return INDEX_PAGE;
    }

    private void addCommonModelAttributes(Model model) {
        // Add common attributes here if needed
    }
}