package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is for storing token
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
