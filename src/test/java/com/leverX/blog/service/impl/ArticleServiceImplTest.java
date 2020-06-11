package com.leverX.blog.service.impl;

import com.leverX.blog.model.Article;
import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.User;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class ArticleServiceImplTest {
    @Autowired
    private ArticleService articleService;
@Autowired
private UserService userService;

    @Test
    public void getArticleByIdFromDB() {
        Article article = articleService.getArticle(1);
        assertThat(article.getId()).isEqualTo(1);
    }

    @Test
    public void saveNewArticle() {
User user= userService.currentUser();
        Article article=articleService.saveNewArticle(new Article("Test", "It is for test", ArticleStatus.PUBLIC,user, LocalDateTime.now(), LocalDateTime.now(),new ArrayList<>(), new HashSet<>()));
        Assert.assertNotNull(articleService.getArticle(article.getId()));
    }
}
