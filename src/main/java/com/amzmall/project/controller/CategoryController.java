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

    //전부 조회
//    @GetMapping("/categorys")
//    public ResponseEntity<?> getCategoryList() {
//        return ResponseEntity.ok(categoryService.getCategoryList());
//    }

    //depth에 따라 조회
    @GetMapping("/categorys/{depth}")
    public ResponseEntity<?> test(@PathVariable("depth") int depth) {


            return ResponseEntity.ok(categoryService.getDepthOneCategories(depth));

//        else {
//            Long parentCategoryId = 1L;
//            return ResponseEntity.ok(categoryService.depthtest(parentCategoryId,depth));
//        }
    }

}