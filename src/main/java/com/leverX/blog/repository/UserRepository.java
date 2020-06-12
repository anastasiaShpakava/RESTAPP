package com.leverX.blog.repository;

import com.leverX.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

/**
 * Interface for actions with {@link User}
 *
 * @author Shpakova A.
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLoginIgnoreCase(String login);//для авторизации пользователя

    User findByEmailIgnoreCase(String email);

    Optional<User> findById(Integer id);
}
