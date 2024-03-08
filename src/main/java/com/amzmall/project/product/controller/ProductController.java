package com.amzmall.project.product.controller;

import com.amzmall.project.product.domain.entity.Product;
import com.amzmall.project.product.domain.dto.ProductDto;
import com.amzmall.project.product.repository.ProductRepository;
import com.amzmall.project.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "product", description="상품")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private  final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    //상품 추가 ---sweager 파일업로드가 테스트 안되기 때문에 postman으로 처리
    @PostMapping("/new")
    @Operation(summary="상품 생성", description="상품을 생성합니다.")
    public void registerProduct(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("isdiscount") boolean isDiscount,
            @RequestParam("discountprice") int discountPrice,
            @RequestParam("vat") double vat,
            @RequestParam("stockquantity") int stockQuantity,
            @RequestParam("photo") MultipartFile photo
    ){

        ProductDto productDTO = new ProductDto(name,price,isDiscount,discountPrice,vat,stockQuantity);
        productService.registerProduct(productDTO,photo);
    }

    //product 전부 조회
    @GetMapping("/find")
    @Operation(summary="모든 상품 조회", description="모든 상품을 조회합니다.")
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    //product id 값을 참고하여 조회
    @GetMapping("/find/{productid}")
    @Operation(summary="상품 id로 조회", description="해당하는 id의 상품을 찾습니다.")
    public Product findProduct(@PathVariable ("productid")String productid){
        System.out.println(productid); //debug
        return productRepository.findProductByProductId(productid);
    }

    //Product 삭제 ---전부 삭제의 경우 고려하지 않음.
    @PostMapping("/delete/{productid}")
    @Operation(summary="상품 삭제", description="상품을 삭제합니다.")
    public void delete(@PathVariable ("productid") String productid){
        System.out.println(productid);
        productService.deleteProduct(productid);
    }

    //Product 수정 ---02/28 추후 테스트 필요.
    @PostMapping("/update/{productid}")
    @Operation(summary="상품 수정", description="상품을 수정합니다.")
    public void update(@PathVariable ("productid") String productid, @RequestBody Product newProduct){
        System.out.println(productid);
        System.out.println(newProduct);
        productService.updateProduct(productid,newProduct);

    }


}
