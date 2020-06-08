package com.leverX.blog.controller;

import com.leverX.blog.exception.DataBaseException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final ArticleService articleService;
    @PostMapping(value = "articles/{articleId}/comments")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Comment createComment( @Valid @RequestBody Comment comment) throws DataBaseException {
        Comment savedComment = commentService.saveNewComment(comment);
        return savedComment;
    }

    @GetMapping(value = {"/articles/{articleId}/comments"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<Comment> getArticleCommentsList(@PathVariable("articleId") Integer articleId,
                                                @RequestParam(value = "skip", defaultValue = "0") int skip,
                                                @RequestParam(value = "limit", defaultValue = "10") int limit) throws DataBaseException {

        List<Comment> commentPage = commentService.getCommentsOfArticle(articleId,  PageRequest.of(skip / limit, limit));
        return commentPage;
    }

    @GetMapping(value = "/articles/{articleId}/comments/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Comment showComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) throws DataBaseException {
        Comment comment = commentService.getCommentById(commentId, articleId);
        return comment;
    }

    @DeleteMapping(value = "/articles/{articleId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) throws DataBaseException {
        Article article = articleService.getArticle(articleId);
        Comment comment = commentService.getCommentById(commentId, articleId);
        commentService.deleteCommentFromArticle(comment, article);
    }
}
