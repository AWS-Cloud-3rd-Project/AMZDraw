package com.amzmall.project.controller;

import com.amzmall.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categorys")
    public ResponseEntity<?> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }


    @GetMapping("/categorys/{depth}")
    public ResponseEntity<?> test(@PathVariable("depth") int depth) {
        Long parentCategoryId = 1L;
        return ResponseEntity.ok(categoryService.depthtest(parentCategoryId,depth));
    }

}