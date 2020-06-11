package com.leverX.blog.service;


import com.leverX.blog.model.dto.RegistrationRequest;
import com.leverX.blog.model.User;
import org.springframework.stereotype.Service;


/**
 * Interface for {@link com.leverX.blog.service.impl.UserServiceImpl}
 *
 * @author Shpakova A.
 */

public interface UserService {

    User findByEmail(String email);

    User findByLogin(String login);

    User save(User user);

    User findByLoginAndPassword(String login, String password);
    User currentUser();

    /**
     * Method: create new token for user
     *
     */
    void createPasswordResetTokenForUser(String token, User user);

    String validatePasswordResetToken(long id, String token);//для смены пароля

    void changeUserPassword(User user, String password);
}
