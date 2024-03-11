package com.amzmall.project.product.domain.entity;

import com.amzmall.project.product.domain.dto.ProductResDto;
import com.amzmall.project.util.exception.BusinessException;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product")
@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "option")
    private String option;

    @Column(name = "amount")
    private int amount;

    @Column(name = "is_discounted")
    private boolean isDiscount;

    @Column(name = "discount_price")
    private int discountPrice;

    @Column(name = "vat")
    private double vat;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "img")
    private String img;

    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_name")
    private String categoryName;

    public ProductResDto toDto() {
        return ProductResDto.builder()
            .id(id)
            .brand(brand)
            .productCode(productCode)
            .name(name)
            .option(option)
            .amount(amount)
            .isDiscount(isDiscount)
            .discountPrice(discountPrice)
            .vat(vat)
            .stockQuantity(stockQuantity)
            .productDesc(productDesc)
            .status(status)
            .createdAt(String.valueOf(createdAt))
            .img(img)
            .imgPath(imgPath)
            .categoryName(categoryName)
            .build();
    }

    public void decreaseStockQuantity(int quantity) {
        if (this.stockQuantity < quantity) {
            throw new BusinessException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }

}
