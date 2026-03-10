package com.soft.backapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soft.backapp.model.ItemCategory;
import com.soft.backapp.repository.ItemCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {
    final ItemCategoryRepository itemCategoryRepository;
    
    public List<ItemCategory> getAllCategories() {
        return itemCategoryRepository.findAll();
    }

    public List<ItemCategory> searchCategoriesByNameLike(String name) {
        return itemCategoryRepository.findByNameLike(name);
    }
}
