package com.leverX.blog.service.impl;

import com.leverX.blog.model.Tag;
import com.leverX.blog.repository.TagRepository;
import com.leverX.blog.service.TagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
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
    @Caching(
            put = {@CachePut(value = "tagCache", key = "#tag.Id")},
            evict = {@CacheEvict(value = "tagCache", allEntries = true)}
    )
    public Tag saveTag(Tag tag) {
        if (tagRepository.findTagByName(tag.getName()) == null) {
            return tagRepository.saveAndFlush(tag);
        } else return tagRepository.findTagByName(tag.getName());
    }
}

