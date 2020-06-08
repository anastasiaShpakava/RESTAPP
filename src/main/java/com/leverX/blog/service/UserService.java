package com.leverX.blog.service;


import com.leverX.blog.model.RegistrationRequest;
import com.leverX.blog.model.User;


public interface UserService {

    User findByEmail(String email);

    User findByLogin(String login);

    User save(User user);

    User createUser(RegistrationRequest registrationRequest);

    User findByLoginAndPassword(String login, String password);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(long id, String token);//для смены пароля

    void changeUserPassword(User user, String password);
}
