package com.amzmall.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResult {
    private Long id;
    private String name;
    private Long depth;
    private List<CategoryResult> children;

    public static CategoryResult of(Category category){
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setId(category.getId());
        categoryResult.setName(category.getName());
        categoryResult.setDepth(categoryResult.getDepth());

        if(category.getDepth()==1) {
            categoryResult.setChildren(null);
        }else {
            categoryResult.setChildren(category.getChildren().stream().map(CategoryResult::of).collect(Collectors.toList()));

        }
        return categoryResult;

    }
}
