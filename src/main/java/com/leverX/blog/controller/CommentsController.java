package com.leverX.blog.controller;


import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import com.leverX.blog.model.dto.CommentDTO;
import com.leverX.blog.service.ArticleService;
import com.leverX.blog.service.CommentService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author Shpakova A.
 */

@RestController
public class CommentsController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public CommentsController(CommentService commentService, ArticleService articleService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "articles/{articleId}/comments")
    @PreAuthorize("isAuthenticated()")
    @SneakyThrows
    public CommentDTO createComment(@PathVariable("articleId") Integer articleId, @RequestBody CommentDTO comment) {
        Comment newComment = modelMapper.map(comment, Comment.class);
        Comment savedComment = commentService.saveNewComment(newComment, articleId);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @GetMapping(value = {"/articles/{articleId}/comments"})
    public Page<CommentDTO> getArticleCommentsList(@PathVariable("articleId") Integer articleId) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        Page<Comment> commentPage = commentService.getCommentsOfArticle(articleId, pageRequest);
        return commentPage.map(comment -> modelMapper.map(comment, CommentDTO.class));
    }

    @GetMapping(value = "/articles/{articleId}/comments/{commentId}")
    public CommentDTO showComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {
        Comment comment = commentService.getCommentById(commentId, articleId);
        return modelMapper.map(comment, CommentDTO.class);
    }

    @DeleteMapping(value = "/articles/{articleId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {
        Article article = articleService.getArticle(articleId);
        Comment comment = commentService.getCommentById(commentId, articleId);
        commentService.deleteCommentFromArticle(commentId);
    }
}
