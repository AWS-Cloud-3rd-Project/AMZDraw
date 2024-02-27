package com.amzmall.project.puduct.repository;


import com.amzmall.project.puduct.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Product findProductByProductId(String productId);


}
