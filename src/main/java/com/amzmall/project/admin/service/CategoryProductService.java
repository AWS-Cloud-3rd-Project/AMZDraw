package com.amzmall.project.admin.service;

import com.amzmall.project.admin.repository.CategoryProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryProductService {
    private final CategoryProductRepository categoryProductRepository;
}