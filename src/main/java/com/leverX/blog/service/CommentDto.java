package com.leverX.blog.service;

import java.util.List;

public interface CommentDto {
   CommentDto saveComment (CommentDto commentDto);
    void deleteComment(Integer articleId);

    CommentDto findById(Integer id);

    List<CommentDto> findAll();
}
