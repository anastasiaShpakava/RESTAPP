package com.leverX.blog.repository;

import com.leverX.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface for actions with {@link Role}
 *
 * @author Shpakova A.
 */


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByLogin(String firstName); //for security. find role by name
}
