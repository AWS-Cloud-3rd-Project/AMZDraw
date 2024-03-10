package com.amzmall.project.product.controller;

import com.amzmall.project.product.domain.dto.ProductResDto;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductReqDto;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.product.service.ProductService;
import com.amzmall.project.util.dto.SingleResult;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "products", description="상품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ResponseService responseService;

    @PostMapping("/new")
    @Operation(summary="상품 생성", description="상품을 생성합니다.")
    public SingleResult<ProductResDto> registerProduct(
        @Parameter(name = "ProductReqDto", description = "요청 객체", required = true)
        @RequestBody ProductReqDto productReqDto,
        @Parameter(name = "photo", description = "상품 사진", required = true)
        @RequestParam("photo") MultipartFile photo) {
        try {
            return responseService.getSingleResult(productService.registerProduct(productReqDto, photo));
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/find")
    @Operation(summary="모든 상품 조회", description="모든 상품을 조회합니다.")
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    @CrossOrigin(origins = "*") // 모든 도메인에서의 요청 허용
    @GetMapping("/find/{productId}")
    @Operation(summary="상품 id로 조회", description="해당하는 id의 상품을 찾습니다.")
    public Product findProduct(@PathVariable ("productId")int productId){
        return productRepository.findProductById(productId);
    }

    @PostMapping("/delete/{productId}")
    @Operation(summary="상품 삭제", description="상품을 삭제합니다.")
    public void delete(@PathVariable ("productId") int productId){
        productService.deleteProduct(productId);
    }

    @PostMapping("/update/{productId}")
    @Operation(summary="상품 수정", description="상품을 수정합니다.")
    public void update(@PathVariable ("productId") int productId, @RequestBody Product newProduct){
        productService.updateProduct(productId, newProduct);

    }
}
