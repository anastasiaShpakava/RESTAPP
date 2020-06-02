package com.leverX.blog.service;

import java.util.List;

public interface TagDto {
    TagDto saveTag (TagDto tagDto);
    void deleteTag(Integer tagId);

    TagDto findById(Integer id);

    List<TagDto> findAll();
}
