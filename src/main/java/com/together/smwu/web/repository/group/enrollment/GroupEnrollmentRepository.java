package com.together.smwu.web.repository.group.enrollment;

import com.together.smwu.web.repository.group.Group;
import com.together.smwu.web.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupEnrollmentRepository extends JpaRepository<GroupEnrollment, Long>, GroupEnrollmentRepositoryCustom {

    Optional<GroupEnrollment> findByUserAndGroup(User user, Group group);

    List<GroupEnrollment> findAllByUser(User user);

    List<GroupEnrollment> findAllByGroup(Group group);
}
