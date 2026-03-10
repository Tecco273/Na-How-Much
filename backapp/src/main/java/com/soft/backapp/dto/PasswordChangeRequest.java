package com.soft.backapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private String oldPassword;
    private String NewPassword;
}
