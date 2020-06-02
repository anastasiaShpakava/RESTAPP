package com.leverX.blog.controller;

import com.leverX.blog.model.dto.UserDto;
import com.leverX.blog.exception.ValidationException;
import com.leverX.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public UserDto saveUsers(@RequestBody UserDto userDto) throws ValidationException {
        log.info("Handling save users: " + userDto);
        return userService.saveUser(userDto);
    }

    @GetMapping("/findAll")
    public List<UserDto> findAllUsers() {
        log.info("Handling find all users request");
        return userService.findAll();
    }

    @GetMapping("/findByFirstName")
    public UserDto findByFirstName(@RequestParam String firstName) {
        log.info("Handling find by first name request: " + firstName);
        return userService.findByFirstName(firstName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Integer id) {
        log.info("Handling delete user request: " + id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}