package com.soft.backapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soft.backapp.model.Item;
import com.soft.backapp.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> searchItemsByNameLike(String name) {
        return itemRepository.findByNameLike(name);
    }

    public List<Item> searchItemsByCategoryByNameLike(Long categoryId, String name) {
        return itemRepository.findByCategoryByNameLike(categoryId, name);
    }

    public Item getItemById(long id) {
        return itemRepository.findById(id);
    }
}
