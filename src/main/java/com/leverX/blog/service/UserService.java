package com.leverX.blog.service;


import com.leverX.blog.model.dto.RegistrationRequest;
import com.leverX.blog.model.User;


/**
 * Interface for {@link by.epam.project.service.impl.TicketServiceImpl}
 *
 * @author Shpakova A.
 */
public interface UserService {

    User findByEmail(String email);

    User findByLogin(String login);

    User save(User user);

    User createUser(RegistrationRequest registrationRequest);

    User findByLoginAndPassword(String login, String password);

    /**
     * Method: create new token for user
     *
     */
    void createPasswordResetTokenForUser(String token, User user);

    String validatePasswordResetToken(long id, String token);//для смены пароля

    void changeUserPassword(User user, String password);
}
