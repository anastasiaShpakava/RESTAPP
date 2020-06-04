package com.leverX.blog.service;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {

    Page<Article> getArticlesPage(int pageNumber, int pageSize, Sort sort);


    Page<Article> findArticleByTag(List<String> tags, int pageNumber, int pageSize, Sort sort);

    Article saveNewArticle(Article article);

    void deleteArticle(Article article);

    void changeStatus(Integer id, ArticleStatus articleStatus);

    public List<Article> getPublicArticle();
}
