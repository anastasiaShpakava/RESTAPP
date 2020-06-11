package com.leverX.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class is for storing article's data
 *
 * @author Shpakova A.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article")
@Builder

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus articleStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Collection<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article")
    private List<Comment> comments;

    public Article(String title, String text, ArticleStatus status, User user, LocalDateTime createdAt, LocalDateTime updatedAt, List<Comment> comments, Set<Tag> tags) {
        this.title = title;
        this.text = text;
        this.articleStatus = status;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
        this.tags = tags;
    }
}
