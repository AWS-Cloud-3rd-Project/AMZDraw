package com.amzmall.project.repository;

import com.amzmall.project.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findItemByTitle(String title);
}
