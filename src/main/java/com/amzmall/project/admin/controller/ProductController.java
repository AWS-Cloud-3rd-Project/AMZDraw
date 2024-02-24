package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.product.ProductDetailView;
import com.amzmall.project.admin.exception.ProductNotFoundException;
import com.amzmall.project.admin.service.ProductService;
import com.amzmall.project.admin.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private static final String MENU_KEY = "products";

    private final ProductService productService;

    @GetMapping(value = {"/products", "/products/"})
    public String list(Model model) {
        List<ProductDTO> productDTOS = productService.findAll();
        model.addAttribute("products", productDTOS);
        model.addAttribute("menuId", MENU_KEY);
        return "/products/products";
    }

    @GetMapping("/products/product-detail")
    public String detail(@RequestParam Long productId, Model model) {
        Optional<ProductDetailView> optionalProductDetailView = productService.getProductDetail(productId);
        ProductDetailView productDetailView = optionalProductDetailView.orElseThrow(() -> new ProductNotFoundException("Not found product info"));
        model.addAttribute("productDetail", productDetailView);
        model.addAttribute("menuId", MENU_KEY);
        return "/products/product-detail";
    }
}