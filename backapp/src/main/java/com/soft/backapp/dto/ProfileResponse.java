package com.soft.backapp.dto;

import com.soft.backapp.model.MyUser;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int submissionNum;

    public ProfileResponse(MyUser user, int submissionNum){
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.submissionNum = submissionNum;
    }
}
