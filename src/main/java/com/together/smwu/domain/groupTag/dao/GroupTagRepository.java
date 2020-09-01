package com.together.smwu.domain.groupTag.dao;

import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.groupTag.domain.GroupTag;
import com.together.smwu.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupTagRepository extends JpaRepository<GroupTag, Long> {

    Optional<GroupTag> findByGroupAndTag(Group group, Tag tag);

    List<GroupTag> findAllByGroup(Group group);

    List<GroupTag> findAllByTag(Tag tag);
}
