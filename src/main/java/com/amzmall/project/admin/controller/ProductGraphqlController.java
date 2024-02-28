package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.adapter.ProductAdapter;
import com.amzmall.project.admin.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product_admin/products")
public class ProductGraphqlController {

    private final ProductAdapter productAdapter;

    @GetMapping("/list")
    public List<ProductDTO> list(@PageableDefault Pageable pageable) {
        return productAdapter.findAll(pageable);
    }
}
