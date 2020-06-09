package com.leverX.blog.model.dto;


import lombok.Data;


import java.time.LocalDateTime;

/**
 * @author Shpakova A.
 */
@Data
public class CommentDTO {

    private Integer id;

    private String commentText;

    private LocalDateTime createdAt;
}
