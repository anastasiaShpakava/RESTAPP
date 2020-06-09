package com.leverX.blog.controller;


import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.dto.ArticleDTO;
import com.leverX.blog.model.dto.CustomUserDetails;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Shpakova A.
 */

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/articles/{articleId}")
    public ArticleDTO showArticle(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getArticleForReading(articleId);
        return modelMapper.map(article, ArticleDTO.class);
    }

    @GetMapping(value = "/articles")
    public Page<ArticleDTO> getPublicArticles (){
        Page<Article> articlePage = articleService.getArticlesPage(PageRequest.of(1,10,Sort.by(Sort.Direction.ASC, "id")));
        return articlePage.map(article -> modelMapper.map(article, ArticleDTO.class));

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
