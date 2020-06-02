package com.leverX.blog.service;

import com.leverX.blog.model.dto.UserDto;
import com.leverX.blog.exception.ValidationException;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto) throws ValidationException;

    void deleteUser(Integer userId);

    UserDto findByFirstName(String first_name);

    List<UserDto> findAll();
}
