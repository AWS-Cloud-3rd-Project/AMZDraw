package com.amzmall.project.puduct.controller;

import com.amzmall.project.puduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/test")
    public ModelAndView test22(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product-form.html");
        return modelAndView;
    }


    //전부 조회
//    @GetMapping("/categorys")
//    public ResponseEntity<?> getCategoryList() {
//        return ResponseEntity.ok(categoryService.getCategoryList());
//    }

    //depth = 1 parents = null
    @GetMapping("/categories")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(categoryService.getTopLevelCategories());
    }
    //parents != null
    @GetMapping("/categories/{parentId}")
    public ResponseEntity<?> getCategoriesByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.getlowLevelCategories(parentId));

//
    }
}