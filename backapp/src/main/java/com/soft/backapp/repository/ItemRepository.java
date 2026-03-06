package com.soft.backapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soft.backapp.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT * FROM item  WHERE name ILIKE CONCAT('%', ?1, '%')",nativeQuery = true)
    Item findByName(String name);
}
