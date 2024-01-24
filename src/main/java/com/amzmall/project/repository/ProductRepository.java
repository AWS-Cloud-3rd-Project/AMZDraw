package com.amzmall.project.repository;

import com.amzmall.project.model.Item;
import com.amzmall.project.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product findProductByProductId(String productId);

}
