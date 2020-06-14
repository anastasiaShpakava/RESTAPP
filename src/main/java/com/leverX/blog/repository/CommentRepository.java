package com.leverX.blog.repository;

import com.leverX.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface for actions with {@link Comment}
 *
 * @author Shpakova A.
 */


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findCommentsByArticleId(Integer id, Pageable pageable);
}
