package com.leverX.blog.model.dto;

import com.leverX.blog.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private Role role;
}
