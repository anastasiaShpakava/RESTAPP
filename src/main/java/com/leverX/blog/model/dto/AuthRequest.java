package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * This class is for authorization data
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
