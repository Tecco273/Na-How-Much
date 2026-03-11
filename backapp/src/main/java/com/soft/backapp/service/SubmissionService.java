package com.soft.backapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soft.backapp.model.Submission;
import com.soft.backapp.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public void saveSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByItemId(long id) {
        return submissionRepository.findByItemId(id);
    }

    public List<Submission> getSubmissionsByUserId(long id) {
        return submissionRepository.findByUserId(id);
    }

    public List<Submission> getLast5ByItemId(long id) {
        return submissionRepository.getLast5ForItem(id);
    }


}
