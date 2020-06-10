package com.leverX.blog.repository;

import com.leverX.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface for actions with {@link Tag}
 *
 * @author Shpakova A.
 */

public interface TagRepository extends JpaRepository<Tag, Integer> {
 Tag findTagByName(String name);
}
