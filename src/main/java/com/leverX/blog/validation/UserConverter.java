package com.leverX.blog.validation;

import com.leverX.blog.model.dto.UserDto;
import com.leverX.blog.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User fromUserDtoToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUserRole(userDto.getUserRole());
        return user;
    }



    public UserDto fromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .userRole(user.getUserRole())
                .build();
    }
}
