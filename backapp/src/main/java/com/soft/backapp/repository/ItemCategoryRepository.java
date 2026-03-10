package com.soft.backapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soft.backapp.model.ItemCategory;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    List<ItemCategory> findAll();

    @Query(value = "SELECT * FROM item_category  WHERE name ILIKE CONCAT('%', ?1, '%')",nativeQuery = true)
    List<ItemCategory> findByNameLike(String name);
}
