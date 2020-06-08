package com.leverX.blog.controller;


import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.CustomUserDetails;
import com.leverX.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor

public class ArticleController {
 //   private final UserService userService;
    private final ArticleService articleService;

    @GetMapping(value = "/articles/{articleId}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") Integer id) throws ResourceNotFoundException {
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
   @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity createArticle(@RequestBody Article article) {
        articleService.saveNewArticle(article);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles", params = {"tagged"})
    public List<Article> searchByTag(@RequestParam("tagged") String tagsStr) {

        List<String> tagNames = Arrays.stream(tagsStr.split(",")).map(String::trim).map(String::toLowerCase).distinct().collect(Collectors.toList());
       List<Article> articlePage = articleService.findArticleByTag(tagNames);
        return articlePage;
    }


    @DeleteMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable("articleId") Integer articleId) throws ResourceNotFoundException {
        Article article = articleService.getArticle(articleId);
        articleService.deleteArticle(article);
    }


    @GetMapping(value = "/my")
    public ResponseEntity<List<Article>> getAllArticlesForCurrentUser(CustomUserDetails customUserDetails) {
        List<Article> articleList = articleService.findArticleByUserLogin(customUserDetails.getUsername());
        if (articleList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(articleList, HttpStatus.OK);
        }
    }
    @PutMapping(value = "articles/{articleId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public Article updateArticle(@Valid @RequestBody Article updatedData, @PathVariable("articleId") Integer articleId) throws ResourceNotFoundException {

        Article updatedArticle = articleService.updateArticle(updatedData);
        return updatedArticle;
    }

}
