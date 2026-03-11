package com.soft.backapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.model.Item;
import com.soft.backapp.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getAllItems")
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/getItemById")
    public ResponseEntity<?> getItemsById(@RequestParam Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping("/searchItems")
    public ResponseEntity<?> getItemsByNameLike(@RequestParam String query) {
        return ResponseEntity.ok(itemService.searchItemsByNameLike(query));
    }

    @GetMapping("/searchItemsByCategory")
    public ResponseEntity<?> getItemsByCategoryByNameLike(@RequestParam Long categoryId, @RequestParam String query) {
        if(categoryId == 0){
            return ResponseEntity.ok(itemService.searchItemsByNameLike(query));
        }
        return ResponseEntity.ok(itemService.searchItemsByCategoryByNameLike(categoryId, query));
    }

    @PostMapping("/saveItem")
    public ResponseEntity<?> saveItem(@RequestBody Item item) {
        itemService.saveItem(item);
        return ResponseEntity.ok("Item saved successfully");
    }
}
