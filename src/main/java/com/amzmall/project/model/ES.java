package com.amzmall.project.model;

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
    private String id ;
    private String name;
    //상품설명 등 매칭
}
