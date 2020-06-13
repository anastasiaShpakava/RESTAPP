package com.leverX.blog.model.dto;

import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Shpakova A.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer id;
    private String title;
    private String text;
    ArticleStatus articleStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Collection<Tag> tags;
}
