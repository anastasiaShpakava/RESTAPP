package com.leverX.blog.service.impl;

import com.leverX.blog.exception.ResourceNotFoundException;
import com.leverX.blog.model.Article;
import com.leverX.blog.model.Comment;
import com.leverX.blog.model.User;
import com.leverX.blog.repository.ArticleRepository;
import com.leverX.blog.repository.CommentRepository;
import com.leverX.blog.repository.UserRepository;
import com.leverX.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class implements {@link ActivityService}
 *
 * @author Shpakova A.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public Comment saveNewComment(Comment newComment)  {
        Article article = articleRepository.findById(newComment.getArticle().getId()).orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findById(newComment.getUser().getId()).orElseThrow(ResourceNotFoundException::new);
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
    @SneakyThrows
    public List<Comment> getCommentsOfArticle(Integer id, Pageable pageable) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article with such id doesn't exist");
        }

        return commentRepository.findCommentsByArticleId(id,pageable);
    }

    @Override
    @SneakyThrows
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

