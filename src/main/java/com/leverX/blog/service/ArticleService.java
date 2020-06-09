package com.leverX.blog.service;


import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ArticleService {


    List<Article> findArticleByTag(List<String> tags);

    Article updateArticle(Article article);

    List<Article> findArticleByUserLogin(String userLogin);

    Article saveNewArticle(Article article);

    void deleteArticle(Article article);

    List<Article> getPublicArticle();

    Article getArticleForReading(Integer id);

    Article getArticle(Integer id);

Page<Article> getArticlesPage (Pageable pageable);
}
