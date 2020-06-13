package com.leverX.blog.model.dto;

import com.leverX.blog.model.ArticleStatus;
import com.leverX.blog.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Shpakova A.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    @NotBlank
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    ArticleStatus articleStatus;
    @NotBlank
    private LocalDateTime createdAt;
    @NotBlank
    private LocalDateTime updatedAt;
    @NotBlank
    private Collection<Tag> tags;
}
