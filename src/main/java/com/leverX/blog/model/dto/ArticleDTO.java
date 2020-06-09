package com.leverX.blog.model.dto;

import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Shpakova A.
 */

@Data
public class ArticleDTO {
    private Integer id;

    private String title;

    private String text;

    ArticleStatus articleStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Collection<Tag> tags;
}
