package com.together.smwu.domain.tag.dao;

import com.together.smwu.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag save(Tag tag);

    Optional<Tag> getByTagId(long id);

    void deleteByTagId(long id);
}
