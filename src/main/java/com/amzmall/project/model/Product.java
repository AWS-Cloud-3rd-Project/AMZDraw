package com.amzmall.project.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@AllArgsConstructor
@Document(collection = "product")
public class Product {
    @Id
    private String productId;
    private Integer sellerId;
//    private Integer categoryId;
    private Integer productCode;
    private String name;
    private String isOption; //on off 체크 만약 없다면 off로 취급
    private Integer salePrice;
    private String isDiscount; //on off 체크 만약 없다면 off로 취급
    private Integer discountPrice;
    private String vat; //과세 면세 영세 체크
    private Integer stockQuantity;
    private String manufacturer;
    private String brand;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    private Integer ratingMean;
    private Integer reviewCount;
    private String status; //A D D의 경우 null로 모두 처리 A의 경우 처리하지 않음
    private String isDelivery; // on off 없으면 off

    public Product() {
    }


}
