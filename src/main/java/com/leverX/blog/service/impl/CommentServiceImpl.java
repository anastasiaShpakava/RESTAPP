package com.leverX.blog.service.impl;

import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.repository.CommentRepository;
import com.leverX.blog.service.CommentService;
import com.leverX.blog.service.UserService;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

;

/**
 * This class implements {@link CommentService}
 *
 * @author Shpakova A.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final UserService userService;

    public CommentServiceImpl(UserService userService, CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Override
    @SneakyThrows
    @Caching(
            put= { @CachePut(value= "commentCache", key= "#newComment.id") },
            evict= { @CacheEvict(value= "allCommentCache", allEntries= true) }
    )
    public Comment saveNewComment(Comment newComment, Integer articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Article with such id doesn't exists");
        }
        Article article = articleRepository.getOne(articleId);
        newComment.setArticle(article);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setUser(userService.currentUser());
        commentRepository.save(newComment);
        return newComment;
    }

    @Override
    @Transactional
    @CacheEvict (value = "commentCache")
    public void deleteCommentFromArticle(Integer commentId) {
        commentRepository.deleteById(commentId);
    }


    @Override
    @SneakyThrows
    @Cacheable(value= "commentCache", key= "#id",unless = "result.size()==0")
    public Page<Comment> getCommentsOfArticle(Integer id, Pageable pageable) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article with such id doesn't exist");
        }
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        return commentRepository.findCommentsByArticleId(id,pageRequest);
    }

    @Override
    @SneakyThrows
    @Cacheable(value= "commentCache", key= "#commentId")
    public Comment getCommentById(Integer commentId, Integer articleId){
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Article with such id  doesn't exists");
        }
        Comment comment = commentRepository.getOne(commentId);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment with such id doesn't exist");
        } else return comment;
    }
}

