package com.leverX.blog.service.impl;

import com.leverX.blog.model.dto.UserDto;
import com.leverX.blog.exception.ValidationException;
import com.leverX.blog.model.User;
import com.leverX.blog.repository.UserRepository;
import com.leverX.blog.service.UserService;
import com.leverX.blog.validation.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService { //поставить @Transactional
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDto saveUser(UserDto userDto) throws ValidationException {
        validateUserDto(userDto);
        User savedUser = userRepository.save(userConverter.fromUserDtoToUser(userDto));
        return userConverter.fromUserToUserDto(savedUser);
    }

    private void validateUserDto(UserDto userDto) throws ValidationException {
        if (userDto==null) {
            throw new ValidationException("Object user is null");
        }
        if (userDto.getFirstName()==null || userDto.getFirstName().isEmpty()) {
            throw new ValidationException("First name is empty");
        }
    }


    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findByFirstName(String first_name) {
        User user = userRepository.findByFirstName(first_name);
        if (user != null) {
            return userConverter.fromUserToUserDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::fromUserToUserDto)
                .collect(Collectors.toList());
    }

}
