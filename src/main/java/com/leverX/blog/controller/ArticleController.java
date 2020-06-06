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

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final UserService userService;
    private final ArticleService articleService;




}
