package com.leverX.blog.service;

import com.leverX.blog.model.Tag;
import org.springframework.stereotype.Service;

/**
 * Interface for {@link com.leverX.blog.service.impl.TagServiceImpl}
 *
 * @author Shpakova A.
 */


public interface TagService {

    Tag saveTag(Tag tag);
}
