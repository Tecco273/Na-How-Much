package com.soft.backapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.dto.ProfileResponse;
import com.soft.backapp.model.MyUser;
import com.soft.backapp.service.MyUserService;
import com.soft.backapp.service.SubmissionService;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final MyUserService userService;
    private final SubmissionService submissionService;

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile() {
        MyUser user = userService.getProfile();
        return ResponseEntity.ok(new ProfileResponse(user,submissionService.getSubmissionsByUserId(user.getId()).size()));
    }
}