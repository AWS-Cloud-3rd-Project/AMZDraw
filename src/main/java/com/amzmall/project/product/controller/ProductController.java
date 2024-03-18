package com.amzmall.project.product.controller;

import com.amzmall.project.product.domain.dto.ProductResDto;
import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductReqDto;
import com.amzmall.project.product.service.ProductService;
import com.amzmall.project.util.advice.ExMessage;
import com.amzmall.project.util.dto.ListResult;
import com.amzmall.project.util.dto.SingleResult;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "products", description="상품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ResponseService responseService;

    @PostMapping("/new")
    @Operation(summary="상품 생성", description="상품을 생성합니다.")
    public SingleResult<ProductResDto> registerProduct(
        @Parameter(name = "ProductReqDto", description = "요청 객체", required = true)
        @RequestPart("ProductReqDto") ProductReqDto productReqDto,
        @Parameter(name = "photo", description = "대표 사진", required = true)
        @RequestPart("thumbnail") MultipartFile thumbnail,
        @RequestPart("photos") List<MultipartFile> photos) {
        try {
            if (photos.size() > 3) { // 최대 5개의 파일을 허용
                throw new BusinessException(ExMessage.PRODUCT_ERROR_UPLOAD_LIMIT);
            }
            return responseService.getSingleResult(productService.registerProduct(productReqDto, thumbnail, photos));
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping("/find")
    @Operation(summary="모든 상품 조회", description="모든 상품을 조회합니다.")
    public ListResult<ProductResDto> findAllProduct(@Parameter(name = "page", description = "페이지 번호 (0부터)", required = true)
    @RequestParam(name = "page",defaultValue = "0") int page,
        @Parameter(name = "size", description = "페이지 사이즈", required = true)
        @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        try {
            return responseService.getListResult(
                productService.getAllProducts(pageRequest)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*") // 모든 도메인에서의 요청 허용
    @GetMapping("/find/{productId}")
    @Operation(summary="상품 id로 조회", description="해당하는 id의 상품을 찾습니다.")
    public SingleResult<ProductResDto> findProduct(
        @PathVariable ("productId")int productId) {
        try {
            return responseService.getSingleResult(
                productService.getOneProduct(productId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{productId}")
    @Operation(summary="상품 삭제", description="상품을 삭제합니다.")
    public void delete(@PathVariable ("productId") int productId){
        try {
            productService.deleteProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @PostMapping("/update/{productId}")
    @Operation(summary="상품 수정", description="상품을 수정합니다.")
    public void update(@PathVariable ("productId") int productId, @RequestBody Product newProduct){
        try {
            productService.updateProduct(productId, newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
