package com.leverX.blog.controller;


import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.dto.ArticleDTO;
import com.leverX.blog.model.dto.CustomUserDetails;
import com.leverX.blog.model.dto.TagCloud;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shpakova A.
 */

@RestController
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleController(UserService userService, ArticleService articleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/articles/{articleId}")
    public ArticleDTO showArticle(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getArticleForReading(articleId);
        return modelMapper.map(article, ArticleDTO.class);
    }

    @GetMapping(value = "/articles")
    @Cacheable("article")
    public Page<ArticleDTO> getPublicArticles() {
        Page<Article> articlePage = articleService.getArticlesPage(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    @PostMapping(value = "/articles")
    @PreAuthorize("isAuthenticated()")
    public ArticleDTO createArticle(@RequestBody ArticleDTO article) {
        Article newArticle = modelMapper.map(article, Article.class);
        Article savedArticle = articleService.saveNewArticle(newArticle);
        return modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @GetMapping(value = "/articles")
    public Page<ArticleDTO> searchByTag(@RequestParam(name = "tagString") String tagString) {
        List<String> tagNames = Arrays.stream(tagString.split(",")).map(String::trim).map(String::toLowerCase).distinct().collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        Page<Article> articlePage = articleService.findArticleByTag(tagNames, pageRequest);
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    @DeleteMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteArticle(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getArticle(articleId);
        articleService.deleteArticle(articleId);
    }

    @GetMapping(value = "/{login}/articles")
    @PreAuthorize("isAuthenticated()")
    public Page<ArticleDTO> getAllArticlesForCurrentUser(CustomUserDetails customUserDetails) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        Page<Article> articleList = articleService.findArticleByUserLogin(customUserDetails.getUsername(), pageRequest);
        return articleList.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    @PutMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public ArticleDTO updateArticle(@RequestBody ArticleDTO editedData, @PathVariable("articleId") Integer articleId) throws ResourceNotFoundException {
        Article articleEdit = articleService.getArticle(articleId);
        Article updatedArticle = articleService.updateArticle(articleEdit, editedData);
        return modelMapper.map(updatedArticle, ArticleDTO.class);
    }

    @GetMapping("articles/changeStatus/{id}")
    public void changeStatus(@PathVariable Integer id, @RequestParam(value = "status") ArticleStatus status) {
        articleService.changeStatus(id, status);
    }

    @PutMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public ArticleDTO editArticle(@Valid @RequestBody ArticleDTO editedData, @PathVariable("articleId") Integer articleId) {
        Article articleToEdit = articleService.getArticle(articleId);
        Article updatedArticle = articleService.updateArticle(articleToEdit, editedData);
        return modelMapper.map(updatedArticle, ArticleDTO.class);
    }

    @GetMapping(value = "tagCloud", params = {"tags"})
    @ResponseStatus(value = HttpStatus.OK)
    public TagCloud getArticlesAmountWithTag(@RequestParam("tags") String tags) {
        List<String> tagNames = Arrays.stream(tags.split(",")).map(String::trim).map(String::toLowerCase).distinct().collect(Collectors.toList());
        Integer count = articleService.amountArticlesWithTag(tagNames);
        return new TagCloud(tagNames, count);
    }
}
