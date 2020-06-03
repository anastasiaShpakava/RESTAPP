package com.leverX.blog.service;

import com.leverX.blog.model.User;


import java.util.List;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    boolean emailExists(String email);

    User findByEmailAndPassword(String email, String password);
}
