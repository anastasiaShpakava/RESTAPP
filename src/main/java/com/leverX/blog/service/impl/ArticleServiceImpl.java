package com.leverX.blog.service.impl;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import com.leverX.blog.model.dto.ArticleDto;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.TagService;
import com.leverX.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final UserService userService;

    @Override
    public Page<Article> getArticlesPage(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return articleRepository.findAll(pageRequest); //добавить после секьюрити в завис-ти от того,аноним или зарегистр., какие публикации может видеть
    }


    @Override
    public Page<Article> findArticleByTag(List<String> tags, int pageNumber, int pageSize, Sort sort) {
        tags = tags.stream().map(String::toLowerCase).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
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
    public Article createArticle(Article article, ArticleDto articleDto) {
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        article.setUpdatedAt(LocalDateTime.now());
        article.setArticleStatus(articleDto.getArticleStatus());
        if (articleDto.getTags() != null) {
            Collection<Tag> editedTags = articleDto.getTags();
            Collection<Tag> articleTags = new ArrayList<>();
            for (Tag tag : editedTags) {
                articleTags.add(tagService.saveTag(tag));
            }
            article.setTags((Set<Tag>) articleTags);
        }
        return articleRepository.saveAndFlush(article);
    }
}