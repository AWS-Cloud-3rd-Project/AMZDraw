package com.amzmall.project.product.domain.dto;

import com.amzmall.project.product.domain.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDto {
    private int id;
    private String brand;
    private String productCode;
    private String name;
    private String option;
    private int amount;
    private boolean isDiscount;
    private int discountPrice;
    private double vat;
    private int stockQuantity;
    private String productDesc;
    private ProductStatus status;
    private String createdAt;
    private String img;
    private String imgPath;
    private String thumbnail;
    private String categoryName;
}
