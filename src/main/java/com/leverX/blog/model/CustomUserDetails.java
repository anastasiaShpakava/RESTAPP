package com.leverX.blog.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.id=user.getId();
        customUserDetails.firstName=user.getFirstName();
        customUserDetails.lastName=user.getLastName();
        customUserDetails.password=user.getPassword();
        customUserDetails.email=user.getEmail();
        customUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().getName()));
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
        return lastName;
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