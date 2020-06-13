package com.leverX.blog.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author Shpakova A.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    @NotBlank
    private Integer id;
    @NotBlank
    private String commentText;
    @NotBlank
    private LocalDateTime createdAt;
}
