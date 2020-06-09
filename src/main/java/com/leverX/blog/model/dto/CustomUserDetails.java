package com.leverX.blog.model.dto;

import com.leverX.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;

/**
 * @author Shpakova A.
 */
public class CustomUserDetails implements UserDetails {

    private String login;
    private String password;


    private Collection<? extends GrantedAuthority> grantedAuthorities;  //интерфейс для доступов пользователя

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails();

        customUserDetails.login = user.getLogin();
        customUserDetails.password = user.getPassword();
        customUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}