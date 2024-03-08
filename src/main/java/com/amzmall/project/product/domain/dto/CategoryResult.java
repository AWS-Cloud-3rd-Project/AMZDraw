package com.amzmall.project.product.domain.dto;

import com.amzmall.project.product.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResult {
    private int id;
    private String name;
    private int depth;
    private int parent;
    private List<CategoryResult> children;

    public static CategoryResult of(Category category) {
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setId(category.getId());
        categoryResult.setName(category.getName());
        categoryResult.setDepth(category.getDepth());
        categoryResult.setParent(categoryResult.getParent());

        if(category.getDepth()==1) {
            categoryResult.setChildren(null);
        }else if(category.getDepth()==2) {
            categoryResult.setChildren(null);
            //문제 발생?
//            categoryResult.setChildren(category.getChildren().stream().map(CategoryResult::of).collect(Collectors.toList()));

        }
        return categoryResult;

    }
}
