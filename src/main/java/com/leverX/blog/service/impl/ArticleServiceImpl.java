package com.leverX.blog.service.impl;

import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.AccessControlException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TagService tagService;

    @Override
    public List<Article> findArticleByTag(List<String> tags) {
        tags = tags.stream().map(String::toLowerCase).collect(Collectors.toList());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return articleRepository.findPublicArticlesByTags(tags);
        }
        return articleRepository.findArticlesByTags(tags);
    }

    @Override
    public List<Article> findArticleByUserLogin(String userLogin) {
        return articleRepository.findArticlesByUserId(userLogin);
    }


    @Override
    public Article saveNewArticle(Article newArticle) {
        return articleRepository.save(Article.builder()
                .title(newArticle.getTitle())
                .text(newArticle.getText())
                .articleStatus(newArticle.getArticleStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }
    @Override
    @SneakyThrows
    public Article getArticle(Integer id) {
        Article article = articleRepository.getOne(id);
        if (article == null) {
            throw new ResourceNotFoundException("Article with such id is not found");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken && article.getArticleStatus() != ArticleStatus.PUBLIC) {
            throw new AccessControlException("You have no permission to view this article. You should Log in");
        } else return article;
    }

    @Override
    @Transactional
    public Article updateArticle(Article article) {
        article.setText(article.getText());
        article.setTitle(article.getTitle());
        article.setUpdatedAt(LocalDateTime.now());
        article.setArticleStatus(article.getArticleStatus());
        if (article.getTags() != null) {
            Collection<Tag> editedTags = article.getTags();
            Collection<Tag> articleTags = new ArrayList<>();
            for (Tag tag : editedTags) {
                articleTags.add(tagService.saveTag(tag));
            }
            article.setTags(articleTags);
        }
        return articleRepository.saveAndFlush(article);
    }


    @Override
    @Transactional
    public void deleteArticle(Article article) {
        articleRepository.delete(article.getId());
    }

    @Override
    public List<Article> getPublicArticle() {
        List<Article> publicArticles = new ArrayList<>();
        for (Article article : articleRepository.getAll()) {
            if (article.getArticleStatus().equals(ArticleStatus.PUBLIC)) {
                publicArticles.add(article);
            }
        }
        return publicArticles;
    }

    @SneakyThrows
    @Override
    @Transactional
    public Article getArticleForReading(Integer id)  {
        Article article = articleRepository.getOne(id);
        if (article == null) {
            throw new ResourceNotFoundException( "Article with such id is not found");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken && article.getArticleStatus() != ArticleStatus.PUBLIC) {
            throw new AccessControlException("You have no permission to view this article. You should Log in");
        } else {
            Hibernate.initialize(article.getTags());
            return article;
        }
    }
}
