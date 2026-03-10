package com.soft.backapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.service.ItemCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class ItemCategoryController {
    private final ItemCategoryService ItemCategoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(ItemCategoryService.getAllCategories());
    }

    @GetMapping("/searchCategories")
    public ResponseEntity<?> getCategoriesByNameLike(@RequestParam String query) {
        return ResponseEntity.ok(ItemCategoryService.searchCategoriesByNameLike(query));
    }
}
