package com.amzmall.project.controller;

import com.amzmall.project.model.ProductDTO;
import com.amzmall.project.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/new")
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
        //dto파람을 받아서 service로 보낸다.
        //service에서 판매자 등등 데이터추가해서 to entity로 해서 db저장
    }
}
