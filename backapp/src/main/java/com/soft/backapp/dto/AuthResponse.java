package com.soft.backapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String msg;
    private String token;
    private Long userId;

    public AuthResponse(String msg, String token, Long userId) {
        this.msg = msg;
        this.token = token;
        this.userId = userId;
    }

    public AuthResponse(String msg) {
        this.msg = msg;
    }
}
