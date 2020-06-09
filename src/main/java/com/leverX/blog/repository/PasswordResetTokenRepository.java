package com.leverX.blog.repository;

import com.leverX.blog.model.Comment;
import com.leverX.blog.model.dto.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for actions with {@link PasswordResetToken}
 *
 * @author Shpakova A.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
