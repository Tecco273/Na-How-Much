package com.soft.backapp.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "submission")
@Getter
@Setter
@Entity
@Data
public class Submission {
    @Id
    private Long id;
    private Long userId;
    private Long itemId;
    private int price;
    private Date purchaseDate;
    private String receiptImageUrl;
    private String location;
}
