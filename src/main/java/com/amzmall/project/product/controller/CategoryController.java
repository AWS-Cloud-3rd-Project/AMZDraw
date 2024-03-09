//package com.amzmall.project.product.controller;
//
//import com.amzmall.project.product.service.CategoryService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "category", description="카테고리")
//@RestController
//@RequiredArgsConstructor
//
//public class CategoryController {
//    private final CategoryService categoryService;
//
////    @GetMapping("/categories")
////    @Operation(summary="카테고리 조회", description="모든 카테고리 조회")
////    public ResponseEntity<?> test() {
////        return ResponseEntity.ok(categoryService.getTopLevelCategories());
////    }
////    //parents != null
////    @GetMapping("/categories/{parentId}")
////    @Operation(summary="부모에 따른 카테고리 조회", description="부모 카테고리 조회")
////    public ResponseEntity<?> getCategoriesByParentId(@PathVariable Long parentId) {
////        return ResponseEntity.ok(categoryService.getlowLevelCategories(parentId));
////
////
////    }
//}