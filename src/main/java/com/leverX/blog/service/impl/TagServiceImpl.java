package com.leverX.blog.service.impl;

import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.TagRepository;
import com.leverX.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class implements {@link TagService}
 *
 * @author Shpakova A.
 */
@Service("tagService")
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    @Override
    public Tag saveTag(Tag tag) {
        if (tagRepository.findTagByName(tag.getName()) == null) {
            return tagRepository.saveAndFlush(tag);
        } else return tagRepository.findTagByName(tag.getName());
    }
}

