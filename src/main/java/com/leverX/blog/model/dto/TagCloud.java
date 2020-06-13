package com.leverX.blog.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagCloud {
    private List<String> tagName;
    private Integer articleCount;
    public TagCloud (List<String> tagName, Integer articleCount) {
        this.tagName = tagName;
        this.articleCount = articleCount;
    }
}
