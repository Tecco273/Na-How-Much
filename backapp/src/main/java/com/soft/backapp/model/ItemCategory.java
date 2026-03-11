package com.soft.backapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "item_category")
@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory {
    @Id
    private Long id;
    private String name;
}
