package com.leverX.blog.service.impl;

import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.TagRepository;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;


    @Override
    public Tag saveTag(Tag tag) {
        if (tagRepository.findTagByName(tag.getName()) == null) {
            return tagRepository.saveAndFlush(tag);
        } else return tagRepository.findTagByName(tag.getName());
    }
}

