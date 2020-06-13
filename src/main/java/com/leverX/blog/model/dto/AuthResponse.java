package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * This class is for storing token
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @NotBlank
    private String token;
}
