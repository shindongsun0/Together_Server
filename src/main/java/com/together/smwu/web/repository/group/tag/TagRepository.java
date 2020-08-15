package com.together.smwu.web.repository.group.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag save(Tag tag);

    Optional<Tag> getByTagId(long id);

    void deleteByTagId(long id);
}
