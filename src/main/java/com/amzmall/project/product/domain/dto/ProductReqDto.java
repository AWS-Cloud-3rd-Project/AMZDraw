package com.amzmall.project.product.domain.dto;

import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.entity.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    @Schema(description = "상품명")
    private String name;
    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Schema(description = "가격")
    private int amount;
    @Schema(description = "할인 여부")
    private boolean isDiscount;
    @Schema(description = "할인 가격")
    private int discountPrice;
    @NotNull(message = "재고는 필수 입력 값입니다.")
    @Schema(description = "재고 수량")
    private int stockQuantity;
    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    @Schema(description = "카테고리 명")
    private String categoryName;
    @Schema(description = "브랜드 명")
    private String brand;
    
    public Product toEntity() {
        return Product.builder()
            .productCode(UUID.randomUUID().toString())
            .name(name)
            .amount(amount)
            .isDiscount(isDiscount)
            .discountPrice(discountPrice)
            .categoryName(categoryName)
            .vat(0.1)
            .stockQuantity(stockQuantity)
            .status(ProductStatus.ACTIVE)
            .brand(brand)
            .build();
    }
}
