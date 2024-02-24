package com.amzmall.project.admin.controller;

import com.amzmall.project.admin.adapter.ProductAdapter;
import com.amzmall.project.admin.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductGraphqlController {

    private final ProductAdapter productAdapter;

    @GetMapping(value = "/products/list")
    public List<ProductDTO> list(@PageableDefault Pageable pageable) {
        return productAdapter.findAll(pageable);
    }
}