package com.leverX.blog.service.impl;

import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import com.leverX.blog.model.dto.ArticleDTO;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

/**
 * This class implements {@link ArticleService}
 *
 * @author Shpakova A.
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;

    @Override
    public Page<Article> findArticleByTag(List<String> tags, Pageable pageable) {
        tags = tags.stream().map(String::toLowerCase).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return articleRepository.findPublicArticlesByTags(tags, pageRequest);
        }
        return articleRepository.findArticlesByTags(tags, pageRequest);
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
    public void changeStatus(Integer id, ArticleStatus status) {
        articleRepository.getOne(id).setArticleStatus(status);
    }

    @Override
    public Page<Article> getArticlesPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return articleRepository.findAllByStatus(ArticleStatus.PUBLIC, pageRequest);
        } else return articleRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public Article updateArticle(Article article, ArticleDTO editedArticle) {
        article.setText(editedArticle.getText());
        article.setTitle(editedArticle.getTitle());
        article.setUpdatedAt(LocalDateTime.now());
        article.setArticleStatus(editedArticle.getArticleStatus());
        if (article.getTags() != null) {
            Collection<Tag> editedTags = editedArticle.getTags();
            Collection<Tag> articleTags = new ArrayList<>();
            for (Tag tag : editedTags) {
                articleTags.add(tagService.saveTag(tag));
            }
            article.setTags(articleTags);
        }
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> findArticleByUserId(String userLogin, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        return articleRepository.findAllByUserLogin(userLogin, pageRequest);
    }


    @Override
    @Transactional
    public void deleteArticle(Article article) {
        articleRepository.delete(article.getId());
    }

    @SneakyThrows
    @Override
    @Transactional
    public Article getArticleForReading(Integer id) {
        Article article = articleRepository.getOne(id);
        if (article == null) {
            throw new ResourceNotFoundException("Article with such id is not found");
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
