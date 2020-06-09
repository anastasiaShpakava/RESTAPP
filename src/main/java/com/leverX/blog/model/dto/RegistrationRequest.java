package com.leverX.blog.model.dto;


import lombok.Data;


import javax.validation.constraints.NotEmpty;

/**
 * This class is for storing  data for registration
 *
 * @author Shpakova A.
 */
@Data
public class RegistrationRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

}
