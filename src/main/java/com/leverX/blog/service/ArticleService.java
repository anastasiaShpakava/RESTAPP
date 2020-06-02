package com.leverX.blog.service;

import com.leverX.blog.model.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto saveArticle (ArticleDto articleDto);
    void deleteArticle(Integer articleId);

    ArticleDto findById(Integer id);

    List<ArticleDto> findAll();
}
