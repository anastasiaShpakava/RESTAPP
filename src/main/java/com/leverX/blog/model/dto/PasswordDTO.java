package com.leverX.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * This class is for new password when password was changed.
 *
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String token;
    @NotBlank
    private String newPassword;
}
