package com.leverX.blog.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Integer id;
    private String text;
    private Integer article_id;
    private Integer creator_id;
    private LocalDateTime createdAt;
}
