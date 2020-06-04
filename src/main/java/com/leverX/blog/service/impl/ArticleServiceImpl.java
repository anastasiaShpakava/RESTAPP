package com.leverX.blog.service.impl;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TagService tagService;

    @Override
    public Page<Article> getArticlesPage(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return articleRepository.findArticlesByStatus(ArticleStatus.PUBLIC, pageRequest);
        } else return articleRepository.findAll(pageRequest);
    }


    @Override
    public Page<Article> findArticleByTag(List<String> tags, int pageNumber, int pageSize, Sort sort) {
        tags = tags.stream().map(String::toLowerCase).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return articleRepository.findPublicArticlesByTags(tags, pageRequest);
        }
        return articleRepository.findArticlesByTags(tags, pageRequest);
    }


    @Override
    @Transactional
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
    @Transactional
    public void deleteArticle(Article article) {
        articleRepository.delete(article.getId());
    }

    @Override
    public void changeStatus(Integer id, ArticleStatus articleStatus) {
        articleRepository.getArticleById(id).setArticleStatus(articleStatus);
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
}
