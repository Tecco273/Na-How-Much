package com.soft.backapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String msg;
    private String token;
    public AuthResponse(String msg, String token) {
        this.msg = msg;
        this.token = token;
    }
}
