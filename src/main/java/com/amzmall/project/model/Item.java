package com.amzmall.project.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@ToString
@Getter

@Document(collection = "item")
public class Item {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private int quantity;


//    @NoArgsConstructor
    public Item() {
    }

//    @AllArgsConstructor
    public Item(String id, String title, String description, double price, int quantity) {
        this.id=id;
        this.title=title;
        this.description=description;
        this.price=price;
        this.quantity=quantity;
    }
}
