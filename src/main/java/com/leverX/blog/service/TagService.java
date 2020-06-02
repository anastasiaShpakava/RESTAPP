package com.leverX.blog.service;

import com.leverX.blog.model.Tag;

import java.util.Optional;

public interface TagService {
    Optional<Tag> saveTag(Tag tag);
}
