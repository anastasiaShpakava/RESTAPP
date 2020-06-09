package com.leverX.blog.model.dto;

import lombok.Data;


/**
 * This class is for new password when password was changed.
 *
 * @author Shpakova A.
 */
@Data
public class PasswordDTO {
    private String oldPassword;
    private String token;
    private String newPassword;
}
