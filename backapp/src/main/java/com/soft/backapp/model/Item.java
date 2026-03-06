package com.soft.backapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "item")
@Getter
@Setter
@Entity
@Data
public class Item {
    @Id
    private Long id;
    private String name;
    private Double avgPrice;
    private String imageUrl;
}
