package com.leverX.blog.service.impl;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (SpringRunner.class)
@SpringBootTest

public class ArticleServiceImplTest  {

    @Autowired
    ArticleServiceImpl articleService;


    @Test
    public void findArticleByTag() {
    }

    @Test
    public void saveNewArticle() {
    }

    @Test
    public void getArticle() {

        Assert.assertEquals(articleService.getArticle(1), "dddd");
    }

    @Test
    public void changeStatus() {
    }

    @Test
    public void getArticlesPage() {
    }

    @Test
    public void updateArticle() {
    }

    @Test
    public void findArticleByUserId() {
    }

    @Test
    public void deleteArticle() {
    }

    @Test
    public void getArticleForReading() {
    }
}