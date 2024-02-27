package com.amzmall.project.puduct.controller;

import com.amzmall.project.puduct.domain.entity.Product;
import com.amzmall.project.puduct.domain.dto.ProductDTO;
import com.amzmall.project.puduct.repository.ProductRepository;
import com.amzmall.project.puduct.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private  final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    //상품 추가
    @PostMapping("/new")
    public void registerProduct(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("isdiscount") boolean isDiscount,
            @RequestParam("discountprice") int discountPrice,
            @RequestParam("vat") double vat,
            @RequestParam("stockquantity") int stockQuantity,
            @RequestParam("photo") MultipartFile photo
    ){

        ProductDTO productDTO = new ProductDTO(name,price,isDiscount,discountPrice,vat,stockQuantity);
        productService.registerProduct(productDTO,photo);
    }

    //product 전부 조회
    @GetMapping("/view")
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }
    //product id 값을 참고하여 조회
    @GetMapping("/view/{productid}")
    public Product findProduct(@PathVariable ("productid")String productid){
        System.out.println(productid);
        return productRepository.findProductByProductId(productid);
    }
    //삭제시 데이터는 모두 null값으로 update

    // 수정은 나중에



}
