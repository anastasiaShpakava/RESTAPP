package com.leverX.blog.service;

import com.leverX.blog.exception.DataBaseException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {


  List<Article> findArticleByTag(List<String> tags);

    Article saveNewArticle(Article article);

    void deleteArticle(Article article);

    void changeStatus(Integer id, ArticleStatus articleStatus);

   List<Article> getPublicArticle();

    Article getArticleForReading(Integer id) throws DataBaseException;
}
