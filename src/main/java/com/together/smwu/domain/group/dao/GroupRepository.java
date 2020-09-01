package com.together.smwu.domain.group.dao;

import com.together.smwu.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {

    Group save(Group group);

    Optional<Group> findById(Long id);

    Optional<Group> findByTitle(String title);
}
