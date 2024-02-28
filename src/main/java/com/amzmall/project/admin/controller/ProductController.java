package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.domain.product.ProductDetailView;
import com.amzmall.project.admin.exception.ProductNotFoundException;
import com.amzmall.project.admin.service.ProductService;
import com.amzmall.project.admin.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private static final String MENU_KEY = "products";
    private static final String PRODUCTS_PAGE = "/products/products";
    private static final String PRODUCT_DETAIL_PAGE = "/products/product-detail";

    private final ProductService productService;

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<ProductDTO> productDTOS = productService.findAll();
        addCommonModelAttributes(model);
        model.addAttribute("products", productDTOS);
        return PRODUCTS_PAGE;
    }

    @GetMapping("/product-detail")
    public String detail(@RequestParam Long productId, Model model) {
        ProductDetailView productDetailView = productService.getProductDetail(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found product info"));
        addCommonModelAttributes(model);
        model.addAttribute("productDetail", productDetailView);
        return PRODUCT_DETAIL_PAGE;
    }

    private void addCommonModelAttributes(Model model) {
        model.addAttribute("menuId", MENU_KEY);
    }
}
