package com.leverX.blog.service;


import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import com.leverX.blog.model.dto.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Interface for {@link com.leverX.blog.service.impl.ArticleServiceImpl}
 *
 * @author Shpakova A.
 */

public interface ArticleService {

    Page<Article> findArticleByTag(List<String> tags,Pageable pageable);

    Article updateArticle(Article article, ArticleDTO editedData);

    Page<Article> findArticleByUserId(String userLogin,Pageable pageable);

    Article saveNewArticle(Article newArticle);

    void deleteArticle(Article article);

    Article getArticleForReading(Integer id);

    Article getArticle(Integer id);

    void changeStatus(Integer id, ArticleStatus status);

    Page<Article> getArticlesPage (Pageable pageable);
}
