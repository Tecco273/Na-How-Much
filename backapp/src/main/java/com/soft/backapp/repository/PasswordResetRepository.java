package com.soft.backapp.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soft.backapp.model.PasswordReset;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long>{

    @Query(value = "SELECT * FROM password_reset  WHERE created_at > ?1",nativeQuery = true)
    public List<Long> findDeletableIds(Date curDate);

    public void deleteByUserEmail(String email);

    public Optional<PasswordReset> findByUserEmail(String email);
}
