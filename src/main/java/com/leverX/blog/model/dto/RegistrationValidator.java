package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationValidator {
    @Size.List({
            @Size(min = 4, message = "Login is too short"),
            @Size(max = 15, message = "Login is too long")
    })
    @NotBlank
    private String login;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;
    @Size.List({
            @Size(min = 3, message = "Password is too short"),
            @Size(max = 45, message = "Password is too long")
    })
    @NotBlank
    private String password;
}
