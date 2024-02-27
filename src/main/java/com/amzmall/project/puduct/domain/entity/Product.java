package com.amzmall.project.puduct.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@DynamicUpdate
@Entity
public class Product {

    @Id
    private String productId ; //상품고유 id 사실상 code와 동일하지 않나?
    private Integer sellerId; //판매자
    private String brand; // 필요한지 체크가 필요하다
//    private Integer categoryId;
//    private Integer productCode; //필요한가?
    private String name; //상품명
//    private String isOption; //on off 체크 만약 없다면 off로 취급
    //가격 따로 빼는건 어떤지 검토가 필요
    //--------------------------------------
    private Integer price; // 판매원가
    private boolean isDiscount; // true시 discountPrice로 변경
    private Integer discountPrice;
    private double vat; //과세 면세 영세 체크 일단은 무조건10퍼로 계산
    //-------------------------------------------------
    private Integer stockQuantity; //재고수량
//    private String manufacturer; //제조사 필요한지 체크가 필요하다
    private String productDesc;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

//    private Integer ratingMean;
//    private Integer reviewCount;

//    private String status; //A D D의 경우 null로 모두 처리 A의 경우 처리하지 않음
//    private String isDelivery; // on off 없으면 off
    //img
    private String img; //상품 사진
    private String imgpath; //사진 위치

    public Product() {
    }


}
