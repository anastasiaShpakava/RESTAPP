package com.leverX.blog.service;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ArticleService {

    Page<Article> getArticlesPage(int pageNumber, int pageSize, Sort sort);


    Page<Article> findArticleByTag(List<String> tags, int pageNumber, int pageSize, Sort sort);

    Article saveNewArticle(Article article);

    void deleteArticle(Article article);

    Article createArticle(Article article, ArticleDto articleDto);
}
