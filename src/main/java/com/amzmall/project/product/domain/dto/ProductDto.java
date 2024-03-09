package com.amzmall.project.product.domain.dto;

import com.amzmall.project.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private int price;
    private boolean isDiscount;
    private int discountPrice;
    private double vat;
    private int stockQuantity;

    public Product toEntity() {
        return Product.builder()
            .productCode(UUID.randomUUID().toString())
            .name(name)
            .price(price)
            .isDiscount(isDiscount)
            .discountPrice(discountPrice)
            .vat(vat)
            .stockQuantity(stockQuantity)
            .build();
    }
}
