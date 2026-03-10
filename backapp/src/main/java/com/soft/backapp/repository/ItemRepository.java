package com.soft.backapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soft.backapp.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT * FROM item  WHERE name ILIKE CONCAT('%', ?1, '%')",nativeQuery = true)
    List<Item> findByNameLike(String name);

    @Query(value = "SELECT * FROM item  WHERE category_id = ?1 AND name ILIKE CONCAT('%', ?2, '%')",nativeQuery = true)
    List<Item> findByCategoryByNameLike(Long categoryId, String name);

    Item findById(long id);

    Item getById(long id);

}
