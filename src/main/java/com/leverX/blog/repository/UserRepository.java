package com.leverX.blog.repository;

import com.leverX.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLoginIgnoreCase(String login);

    User findByEmailIgnoreCase(String email);

    Optional<User> findByLogin(String login); //for security

    User findByLoginOrEmail(String login, String email);

    Optional<User> findById(Integer id);
}
