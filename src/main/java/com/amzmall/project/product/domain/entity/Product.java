package com.amzmall.project.product.domain.entity;

import com.amzmall.project.util.exception.BusinessException;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "product")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int productId ; //상품고유 id 사실상 code와 동일하지 않나?

    @Column(name = "brand")
    private String brand; // 필요한지 체크가 필요하다

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "product_code")
    private String productCode; //필요한가?

    @Column(name = "name")
    private String name; //상품명

    @Column(name = "is_option")
    private String isOption; //on off 체크 만약 없다면 off로 취급
    //가격 따로 빼는건 어떤지 검토가 필요
    //--------------------------------------
    @Column(name = "price")
    private int price; // 판매원가
    @Column(name = "is_discounted")
    private boolean isDiscount; // true시 discountPrice로 변경
    @Column(name = "discount_price")
    private int discountPrice;
    @Column(name = "vat")
    private double vat; //과세 면세 영세 체크 일단은 무조건10퍼로 계산
    //-------------------------------------------------
    @Column(name = "stock_quantity")
    private int stockQuantity; //재고수량
    @Column(name = "manufacturer")
    private String manufacturer; //제조사 필요한지 체크가 필요하다
    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "status")
    private ProductStatus status; //1이면 판매중 0이면 판매중단

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    //img
    @Column(name = "img")
    private String img; //상품 사진

    @Column(name = "imgpath")
    private String imgpath; //사진 위치

    public Product() {
    }

    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if(restStock < 0) {
            throw new BusinessException(("상품의 재고가 부족합니다." +
                "(현재 재고 수량: " + this.stockQuantity + ")"));
        }
        this.stockQuantity = restStock;
    }

}
