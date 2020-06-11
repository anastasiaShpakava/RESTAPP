package com.leverX.blog.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

/**
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer id;

    private String commentText;

    private LocalDateTime createdAt;
}
