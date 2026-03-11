package com.soft.backapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.model.Item;
import com.soft.backapp.model.Submission;
import com.soft.backapp.service.ItemService;
import com.soft.backapp.service.SubmissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;
    private final ItemService itemService;

    @PostMapping("/saveSubmission")
    public ResponseEntity<?> saveSubmission (@RequestBody Submission submission) {
        submissionService.saveSubmission(submission);
        List<Submission> submissions = submissionService.getSubmissionsByItemId(submission.getItemId());
        Item item = itemService.getItemById(submission.getItemId());
        double avgPrice = 0;
        for (Submission s : submissions) {
            avgPrice += s.getPrice();
        }
        avgPrice /= submissions.size();
        item.setAvgPrice(avgPrice);
        itemService.saveItem(item);

        return ResponseEntity.ok("Submission saved successfully");
    }

    @GetMapping("/getLast5ForItem")
    public ResponseEntity<?> getLast5ForItem(@RequestParam Long itemId) {
        return ResponseEntity.ok(submissionService.getLast5ByItemId(itemId));
    }
}
