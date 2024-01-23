package com.amzmall.project.service;

import com.amzmall.project.model.Item;
import com.amzmall.project.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemrepository;

    @Autowired
    public ItemService(ItemRepository itemrepository) {
        this.itemrepository = itemrepository;
    }

    public List<Item> getItemList() {
        return itemrepository.findAll();
    }

    public Item saveItem(Item item){
        return itemrepository.save(item);
    }

    public Item getItemsByTitle(String title){
        Optional<Item> optionalItem  = itemrepository.findItemByTitle(title);
        if(optionalItem .isPresent()) {
            Item item = optionalItem.get();
            System.out.println(item);
            return item;
        }else {
            System.out.println("찾을 수 없다. : " + title);
            return null;
        }

    }
}
