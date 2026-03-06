package com.soft.backapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
