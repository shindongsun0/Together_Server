package com.together.smwu.domain.groupEnrollment.dao;

import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import com.together.smwu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupEnrollmentRepository extends JpaRepository<GroupEnrollment, Long>, GroupEnrollmentRepositoryCustom {

    Optional<GroupEnrollment> findByUserAndGroup(User user, Group group);

    List<GroupEnrollment> findAllByUser(User user);

    List<GroupEnrollment> findAllByGroup(Group group);
}
