package com.amzmall.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    //폼 형식으로 입력받는 내용
    private String name;
    //가격
    private int price;
    private boolean isDiscount;
    //할인가
    private int discountPrice;
    //부가세
    private double vat;
    //재고
    private int stockQuantity;




    public Product toEntity() {
        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .name(name)
                .price(price)
                .isDiscount(isDiscount)
                .discountPrice(discountPrice)
                .vat(vat)
                .stockQuantity(stockQuantity)
                .build();
    }
}
