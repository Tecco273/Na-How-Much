package com.soft.backapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soft.backapp.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAll();

    List<Submission> findByItemId(Long itemId);

    List<Submission> findByUserId(Long userId);

    @Query(value = "SELECT * FROM submission WHERE item_id = ?1 ORDER BY purchase_date DESC LIMIT 5",nativeQuery = true)
    List<Submission> getLast5ForItem(Long itemId);
}
