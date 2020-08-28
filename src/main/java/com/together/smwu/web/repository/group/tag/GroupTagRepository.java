package com.together.smwu.web.repository.group.tag;

import com.together.smwu.web.repository.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupTagRepository extends JpaRepository<GroupTag, Long> {

    Optional<GroupTag> findByGroupAndTag(Group group, Tag tag);

    List<GroupTag> findAllByGroup(Group group);

    List<GroupTag> findAllByTag(Tag tag);
}
