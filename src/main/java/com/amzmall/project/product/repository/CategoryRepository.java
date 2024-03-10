package com.amzmall.project.product.repository;

import com.amzmall.project.product.domain.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String categoryName);
}
