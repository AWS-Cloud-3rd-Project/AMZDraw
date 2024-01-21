package com.amzmall.project.model;

import lombok.Getter;

@Getter
public class ItemDTO {
    private String title;
    private String description;
    private double price;
    private int quantity;

    public Item toEntity() {
        return Item.builder()
                .title(title)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
