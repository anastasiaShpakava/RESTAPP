package com.leverX.blog.service;

import com.leverX.blog.exception.DataBaseException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveNewComment(Comment comment) throws DataBaseException;

    void deleteCommentFromArticle(Comment comment, Article article);

  List<Comment> getCommentsOfArticle(Integer id) throws DataBaseException;
}
