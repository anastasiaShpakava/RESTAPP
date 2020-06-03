package com.leverX.blog.repository;

import com.leverX.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findCommentsByArticleId(Integer id, Pageable pageable);

    void delete(Integer id);//?

//    Page<Comment> findAllByUserId(Integer id, Pageable pageable);
//
//    Page<Comment> findAllByUserEmail(String email, Pageable pageable);
}
