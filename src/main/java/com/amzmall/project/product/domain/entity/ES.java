package com.amzmall.project.product.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ES {
    @Id
    private int id ;
    private String name;
    private String itemdesc;
}
