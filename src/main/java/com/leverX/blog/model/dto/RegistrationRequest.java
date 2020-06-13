package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

/**
 * This class is for storing  data for registration
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;

}
