package com.leverX.blog.service.impl;

import com.leverX.blog.exception.DataBaseException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import com.leverX.blog.model.User;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.repository.CommentRepository;
import com.leverX.blog.repository.UserRepository;
import com.leverX.blog.service.CommentService;
import com.leverX.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional

    public Comment saveNewComment(Comment newComment) throws DataBaseException {
        Article article = articleRepository.findById(newComment.getArticle().getId()).orElseThrow(DataBaseException::new);
        User user = userRepository.findById(newComment.getUser().getId()).orElseThrow(DataBaseException::new);
        return commentRepository.save(Comment.builder()
                .text(newComment.getText())
                .article(article)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public void deleteCommentFromArticle(Comment comment, Article article) {
        commentRepository.delete(comment.getId());
    }


    @Override
    public List<Comment> getCommentsOfArticle(Integer id, Pageable pageable) throws DataBaseException {
        if (!articleRepository.existsById(id)) {
            throw new DataBaseException("Article with id " + id + " doesn't exist");
        }

        return commentRepository.findCommentsByArticleId(id,pageable);
    }

    @Override
    public Comment getCommentById(Integer commentId, Integer articleId) throws DataBaseException {
        if (!articleRepository.existsById(articleId)) {
            throw new DataBaseException("Article with such id " + articleId + " doesn't exists");
        }
        Comment comment = commentRepository.getOne(commentId);
        if (comment == null) {
            throw new DataBaseException("Comment with id " + commentId + " doesn't exist");
        } else return comment;
    }
}

