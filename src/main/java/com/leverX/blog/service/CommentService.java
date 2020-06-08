package com.leverX.blog.service;


import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;

import org.springframework.data.domain.Pageable;


import java.util.List;


public interface CommentService {

    Comment saveNewComment(Comment comment) throws ResourceNotFoundException;

    void deleteCommentFromArticle(Comment comment, Article article);

    List<Comment> getCommentsOfArticle(Integer id, Pageable pageable);

    Comment getCommentById(Integer commentId, Integer articleId);
}
