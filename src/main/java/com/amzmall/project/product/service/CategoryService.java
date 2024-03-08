package com.amzmall.project.product.service;

import com.amzmall.project.product.domain.entity.Category;
import com.amzmall.project.product.domain.dto.CategoryResult;
import com.amzmall.project.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

//    public List<CategoryResult> depthone(int depth){
//        List<CategoryResult> results = categoryRepository.findDepthone(depth).stream().map(CategoryResult::of).collect(Collectors.toList());
//        return results;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public List<CategoryResult> getCategoryList() {
////        System.out.println(1);
////        List<Category> categories = categoryRepository.findAll();
////        System.out.println(2);
//        // Assuming CategoryResult.of(Category) method is available
////        List<CategoryResult> results = categories.stream().map(CategoryResult::of).collect(Collectors.toList());
//
//        List<CategoryResult> results = categoryRepository.findAll().stream().map(CategoryResult::of).collect(Collectors.toList());
//        return results;
//    }
public List<CategoryResult> getTopLevelCategories() {
    List<Category> topLevelCategories = categoryRepository.findAllByParentIsNull();
    return topLevelCategories.stream()
            .map(CategoryResult::of)
            .collect(Collectors.toList());
}
public List<CategoryResult>getlowLevelCategories(Long parentId){

    List<Category> lowLevelCategories = categoryRepository.findByParentIdCustomQuery(parentId);
    return lowLevelCategories.stream()
            .map(CategoryResult::of)
            .collect(Collectors.toList());
}

//    public List<Category> getFirstChildCategories() {
//        // 1차 카테고리 조회
//        List<Category> firstCategories = categoryRepository.findAllByParentIsNull();
//
//        // 첫 번째 1차 카테고리의 자식 카테고리 조회
//        if (!firstCategories.isEmpty()) {
//            Category firstCategory = firstCategories.get(0);
//            List<Category> children = categoryRepository.findByParent(firstCategory);
//            return children;
//        }
//
//        return Collections.emptyList();
//    }

//    public List<CategoryResult> getDepthOneCategories(int depth) {
//        List<Category> depthOneCategories = categoryRepository.findAllByDepth(depth);
//        return depthOneCategories.stream()
//                .map(CategoryResult::of)
//                .collect(Collectors.toList());
//    }

//    public List<CategoryResult> depthtest(Long parentCategoryId,int targetDepth){
//        List<CategoryResult> results = categoryRepository.findChildrenByDepth(parentCategoryId,targetDepth).stream().map(CategoryResult::of).collect(Collectors.toList());
//        return results;
//    }
}
