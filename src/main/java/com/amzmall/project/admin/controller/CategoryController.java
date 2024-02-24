package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.service.dto.PieDemoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
public class CategoryController {

    @GetMapping(value = "/api/sales-per-categories")
    public PieDemoDTO salesPerCategories() {
        List<String> categories = List.of("여성", "남성", "공용");
        List<Integer> sales = List.of(100, 200, 300);
        PieDemoDTO pieDemoDTO = PieDemoDTO.of(categories, sales);
        log.info("Metric API Response = {}", pieDemoDTO);
        return pieDemoDTO;
    }
}