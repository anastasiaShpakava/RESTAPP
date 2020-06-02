package com.leverX.blog.service.impl;

import com.leverX.blog.model.User;
import com.leverX.blog.repository.UserRepository;
import com.leverX.blog.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }
        @Override
        public boolean emailExists (String email){
            return userRepository.existsByEmail(email);
        }
    }

