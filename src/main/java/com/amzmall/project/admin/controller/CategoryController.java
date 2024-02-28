package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.enums.AdminUserRole;
import com.amzmall.project.admin.service.CategoryService;
import com.amzmall.project.admin.service.dto.PieDemoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/product_admin")
public class CategoryController {

    public interface AdminUserRoles {
        String PRODUCT_AD = "ADMIN_PRODUCT";
        String MEMBER_AD = "ADMIN_MEMBER";
        String ORDER_AD = "ADMIN_ORDER";
        String SUPER_AD = "ADMIN_SUPER";
    }


    public CategoryController(CategoryService categoryService) {
    }

    @GetMapping(value = "/sales-per-categories")
    @Secured(AdminUserRoles.PRODUCT_AD)
    public PieDemoDTO salesPerCategories() {
        List<String> categories = List.of("여성", "남성", "공용");
        List<Integer> sales = List.of(100, 200, 300);
        PieDemoDTO pieDemoDTO = PieDemoDTO.of(categories, sales);
        log.info("Metric API Response = {}", pieDemoDTO);
        return pieDemoDTO;
    }
}