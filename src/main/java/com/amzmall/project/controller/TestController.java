package com.amzmall.project.controller;

import com.amzmall.project.model.Item;
import com.amzmall.project.model.ItemDTO;
import com.amzmall.project.repository.Itemrepository;
import com.amzmall.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
public class TestController {

    private final ItemService itemService;
    private final Itemrepository itemrepository;

    @GetMapping("/")
    public String test(){
        System.out.println("test");
        return "test";
    }

    @GetMapping("/find/{title}")
    public Item findById(@PathVariable("title") String title){
        System.out.println(title);
//        Item foundItem = itemService.getItemsByTitle(title);
//        System.out.println(foundItem.toString());
//        if (foundItem != null) {
//            System.out.println(foundItem);
//            return ResponseEntity.ok(foundItem);
//        } else {
//            System.out.println("Item not found for title: " + title);
//            return ResponseEntity.notFound().build();
//        }
        return itemrepository.findById(title).get();
    }

    @PostMapping("/item_register")
    public Item saveItem(@RequestBody ItemDTO itemDTO){
        Item item = itemDTO.toEntity();
        System.out.println(item.toString());
        return itemService.saveItem(item);

    }

}
