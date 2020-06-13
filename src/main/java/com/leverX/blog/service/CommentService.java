package com.leverX.blog.service;


import com.leverX.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for {@link com.leverX.blog.service.impl.CommentServiceImpl}
 *
 * @author Shpakova A.
 */


public interface CommentService {
    Comment saveNewComment(Comment comment, Integer articleId);

    void deleteCommentFromArticle(Integer commentId);

    Page<Comment> getCommentsOfArticle(Integer id, Pageable pageable);

    Comment getCommentById(Integer commentId, Integer articleId);
}
