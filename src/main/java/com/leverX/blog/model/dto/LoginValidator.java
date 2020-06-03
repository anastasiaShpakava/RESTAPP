package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginValidator {
    @Size.List({
            @Size(min = 4, message = "Login is too short"),
            @Size(max = 15, message = "Login is too long")
    })
    @NotBlank
    private String login;

    @Size.List({
            @Size(min = 3, message = "Password is too short"),
            @Size(max = 45, message = "Password is too long")
    })
    @NotBlank
    private String password;
}
