package com.amzmall.project.repository;


import com.amzmall.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, String> {

    Product findProductByProductId(String productId);

}
