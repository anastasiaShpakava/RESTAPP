package com.leverX.blog.model;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String oldPassword;
    private  String token;
    private String newPassword;
}
