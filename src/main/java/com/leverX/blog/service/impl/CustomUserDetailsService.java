package com.leverX.blog.service.impl;

import com.leverX.blog.model.CustomUserDetails;
import com.leverX.blog.model.User;
import com.leverX.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String lastName) throws UsernameNotFoundException {
        User user = userService.findByEmail(lastName);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
//В этом методе я достаю из базы данных моего юзера по lastName, конвертирую его в CustomUser и возвращаю