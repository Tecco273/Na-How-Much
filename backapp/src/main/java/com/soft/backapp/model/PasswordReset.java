package com.soft.backapp.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "password_reset")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String code;
    private Date createdAt;

    public PasswordReset(String userEmail, String code, Date createdAt){
        this.code = code;
        this.createdAt = createdAt;
        this.userEmail = userEmail;
    }
}
