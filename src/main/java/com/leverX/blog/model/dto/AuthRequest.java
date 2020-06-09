package com.leverX.blog.model.dto;

import lombok.Data;
/**
 * This class is for authorization data
 *
 * @author Shpakova A.
 */
@Data
public class AuthRequest {
    private String login;
    private String password;
}
