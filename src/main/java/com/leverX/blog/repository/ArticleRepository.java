package com.leverX.blog.repository;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Interface for actions with {@link Article}
 *
 * @author Shpakova A.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE LOWER  (t.name) IN :tags")
        //в нижн. регистр
    Page<Article> findArticlesByTags(@Param("tags") Collection<String> tags, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE LOWER(t.name) in (:tags) and a.articleStatus = 'PUBLIC'")
    Page<Article> findPublicArticlesByTags(@Param("tags") Collection<String> tags, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.user u WHERE u.login=:userLogin")
    Page<Article> findAllByUserLogin(@Param("userLogin") String userLogin, Pageable pageable);

    @Query(value = "select article.id from article where article.status ='PUBLIC'", nativeQuery = true)
    Page<Article> findAllByStatus(ArticleStatus status, Pageable pageable);

    @Query("SELECT count(a) FROM Article a WHERE :tagCount = (SELECT COUNT(DISTINCT t.id) FROM Article a2 JOIN a2.tags t WHERE LOWER(t.name) in (:tagNames) and a = a2)")
    Integer findArticleByAmountTag(@Param("tagNames") Collection<String> tagNames, @Param("tagCount") Integer tagCount);
}
