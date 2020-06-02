package com.leverX.blog.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private Integer id;
    private String name;
}
