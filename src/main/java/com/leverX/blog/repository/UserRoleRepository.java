package com.leverX.blog.repository;

import com.leverX.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<Role, Integer> {

    Role findByEmail(String email);
}
