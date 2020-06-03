package com.leverX.blog.repository;

import com.leverX.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByLogin(String login); //for security
}
