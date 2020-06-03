package com.leverX.blog.repository;

import com.leverX.blog.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findByEmail(String email);
}
