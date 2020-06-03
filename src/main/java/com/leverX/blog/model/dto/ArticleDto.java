package com.leverX.blog.model.dto;

import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class ArticleDto {
    private Integer id;
    private String title;
    private String text;
    private ArticleStatus articleStatus;
    private Integer author_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Tag> tags;
}
