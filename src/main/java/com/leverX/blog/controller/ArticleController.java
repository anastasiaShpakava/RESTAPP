package com.leverX.blog.controller;


import com.leverX.blog.exception.DataBaseException;
import com.leverX.blog.model.Article;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class ArticleController {
    private final UserService userService;
    private final ArticleService articleService;



    @GetMapping(value = "/articles/{articleId}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") Integer id) throws DataBaseException {
        Article article = articleService.getArticleForReading(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
    @GetMapping(value = "/articles")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity getPublicArticlesList () {
        List<Article> articlePub = articleService.getPublicArticle();
        return new ResponseEntity (articlePub, HttpStatus.OK);

    }

    @PostMapping(value = "/articles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity createArticle(@RequestBody ArticleWrapper article,
                                        @CurrentUser CustomUserDetails currentUser) {
        articleService.createArticle(article.getText(), article.getTitle(), currentUser.getId(), article.getTags());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles", params = {"tagged"})
    public Page<ArticleDTO> searchByTag(@RequestParam("tagged") String tagsStr,
                                        @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "sort", defaultValue = "createdAt") String properties,
                                        @RequestParam(value = "order", defaultValue = "DESC") String order
    ) {
        Sort sort = createSortRequest(order, properties);
        List<String> tagNames = Arrays.stream(tagsStr.split(",")).map(String::trim).map(String::toLowerCase).distinct().collect(Collectors.toList());
        Page<Article> articlePage = articleService.findArticleByTag(tagNames, pageNumber, pageSize, sort);
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));
    }


    @DeleteMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        Article article = articleService.getArticle(articleId);
        articleService.deleteArticle(article);
    }

    @GetMapping(value = "/{username}/articles")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ArticleDTO> getUserArticlesList(@PathVariable("username") String username,
                                                @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "sort", defaultValue = "createdAt") String properties,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order) {
        Sort sort = createSortRequest(order, properties);
        Page<Article> articlePage = articleService.getUserArticlesPage(username, pageNumber, pageSize, sort);
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    @PutMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public ArticleDTO editArticle(@Valid @RequestBody ArticleDTO editedData, @PathVariable("articleId") Long articleId) {
        Article articleToEdit = articleService.getArticle(articleId);
        Article updatedArticle = articleService.updateArticle(articleToEdit, editedData);
        return modelMapper.map(updatedArticle, ArticleDTO.class);
    }

    @GetMapping(value = "tag-cloud", params = {"tags"})
    @ResponseStatus(value = HttpStatus.OK)
    public TagCloudResponse getArticlesWithTagCount(@RequestParam("tags") String tags) {
        List<String> tagNames = Arrays.stream(tags.split(",")).map(String::trim).map(String::toLowerCase).distinct().collect(Collectors.toList());
        Long count = articleService.countArticlesWithTag(tagNames);
        return new TagCloudResponse(tagNames, count);
    }

    @GetMapping(value = "/my")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<ArticleDTO> getUserArticlesList(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "sort", defaultValue = "createdAt") String properties,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order) {
        String myUsername = userService.currentUser().getUsername();
        Sort sort = createSortRequest(order, properties);
        Page<Article> articlePage = articleService.getUserArticlesPage(myUsername, pageNumber, pageSize, sort);
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    //                    =================================
    //                       utility logic. to be replaced
    //                    =================================

    //enum of article properties for validating passed to URL arguments
    //Maybe here we can just throw exception in case of wrong property
    enum ArticleProperties {
        createdAt, updatedAt, title, id, status
    }

    private Sort createSortRequest(String order, String properties) {
        //Splitting passed properties to array and removing invalid properties
        List<String> sortingProperties =
                Arrays.stream(properties.split(","))
                        .map(String::trim)
                        .distinct()
                        .filter(property -> Arrays.stream(ArticleProperties.values()).anyMatch(t -> t.name().equals(property)))
                        .collect(Collectors.toList());
        //Setting default sorting value
        if (sortingProperties.size() == 0) {
            sortingProperties.add("createdAt");
        }
        Sort.Direction sortOrder =
                (order.toUpperCase().equals("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new Sort(sortOrder, sortingProperties);
    }
}
