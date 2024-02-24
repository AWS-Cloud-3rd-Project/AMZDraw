package com.amzmall.project.admin.repository;

import com.amzmall.project.admin.domain.product.Product;
import com.amzmall.project.admin.domain.product.ProductDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            "select new com.amzmall.project.admin.domain.product.ProductDetailView(p.productId, p.productName, p.imageUrl, p.stockQuantity, p.price, p.isExposed, p.vendorId, v.vendorName, p.createdAt, p.createdBy) " +
                    "from Product p left join Vendor v on p.vendorId = v.vendorId "
    )
    List<ProductDetailView> findAllProductDetailView();

    @Query(
            "select new com.amzmall.project.admin.domain.product.ProductDetailView(p.productId, p.productName, p.imageUrl, p.stockQuantity, p.price, p.isExposed, p.vendorId, v.vendorName, p.createdAt, p.createdBy) " +
                    "from Product p left join Vendor v on p.vendorId = v.vendorId " +
                    "where p.productId = :productId"
    )
    Optional<ProductDetailView> getProductDetail(@Param(value = "productId") Long productId);
}