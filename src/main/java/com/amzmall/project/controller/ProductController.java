package com.amzmall.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ProductController {
    @PostMapping("/item/new")
    public String registerProduct(
            @RequestParam("name") String name,
            @RequestParam("text") String text,
            @RequestParam("price") int price,
            @RequestParam("stock") int stock,
            @RequestParam("soldout") boolean soldout,
            @RequestParam("photo") MultipartFile photo
    ){
        return "구아아악";
    }
}
