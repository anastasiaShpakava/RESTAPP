package com.leverX.blog.service.impl;

import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.TagRepository;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Override
    public Optional<Tag> saveTag(Tag tag) {
        return tagRepository.findByName(tag.getName());
    }
}
