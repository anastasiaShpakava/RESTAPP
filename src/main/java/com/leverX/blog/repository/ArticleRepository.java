package com.leverX.blog.repository;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Page<Article> findArticlesByLogin(String userLogin, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE LOWER  (t.name) IN :tags")
        //в нижн. регистр
    Page<Article> findArticlesByTags(@Param("tags") Collection<String> tags, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE LOWER(t.name) in (:tags) and a.articleStatus = 'PUBLIC'")
    Page<Article> findPublicArticlesByTags(@Param("tags") Collection<String> tags, Pageable pageable);


    Page<Article> findArticlesByStatus(ArticleStatus articleStatus, Pageable pageable);

    void delete(Integer id); //?

    Article getArticleById(Integer id);

    List<Article> getAll() ;


}
