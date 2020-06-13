package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is for storing  data for registration
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String login;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
}
