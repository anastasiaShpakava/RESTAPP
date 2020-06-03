package com.leverX.blog.service;

import com.leverX.blog.model.User;
import com.leverX.blog.model.dto.RegistrationRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User findByEmail(String email);

    User findByLogin(String login);

    User save(User user);

    User createUser(RegistrationRequest registrationRequest);

    String generatePasswordResetToken(String email);

    UserDetails loadUserById(Integer id);
}
