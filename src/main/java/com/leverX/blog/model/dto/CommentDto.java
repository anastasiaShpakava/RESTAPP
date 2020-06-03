package com.leverX.blog.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Integer id;
    private String text;
    private LocalDateTime createdAt;
}
