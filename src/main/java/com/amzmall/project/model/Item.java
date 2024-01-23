package com.amzmall.project.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@ToString
@Getter
@AllArgsConstructor
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

}
