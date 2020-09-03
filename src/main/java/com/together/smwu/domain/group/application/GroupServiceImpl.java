package com.together.smwu.domain.group.application;

import com.together.smwu.domain.group.dao.GroupRepository;
import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.group.dto.GroupRequest;
import com.together.smwu.domain.group.dto.GroupResponse;
import com.together.smwu.domain.group.exception.GroupNotFoundException;
import com.together.smwu.domain.groupEnrollment.dao.GroupEnrollmentRepository;
import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import com.together.smwu.domain.groupEnrollment.exception.GroupNotAuthorizedException;
import com.together.smwu.domain.user.domain.User;
import org.springframework.stereotype.Service;

import javax.jdo.annotations.Transactional;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupEnrollmentRepository groupEnrollmentRepository;

    public GroupServiceImpl(GroupRepository groupRepository, GroupEnrollmentRepository groupEnrollmentRepository) {
        this.groupRepository = groupRepository;
        this.groupEnrollmentRepository = groupEnrollmentRepository;
    }

    public Long create(GroupRequest request, User user) {
        Group group = groupRepository.save(request.toGroupEntity());
        enrollGroupWithMasterUser(user, group);
        return group.getId();
    }

    /*
    - findAllGroups(): 모든 group조회 [/all]
    - deleteGroupById(Long id): group삭제 → groupEnrollment의 deleteGroup호출
     */
    @Transactional
    public void update(Long groupId, GroupRequest request, User user) {
        Group group = findGroupIfAuthorized(groupId, user.getMsrl());
        group.update(request.toGroupEntity());
        groupRepository.save(group);
    }

    @Transactional
    public List<GroupResponse> findByTitle(String groupTitle) {
        List<Group> groups = groupRepository.findByTitle(groupTitle);
        if (groups.isEmpty()) {
            throw new GroupNotFoundException(groupTitle);
        }
        return GroupResponse.listFrom(groups);
    }

    @Transactional
    public GroupResponse findByGroupId(Long groupId) {
        Group group = findById(groupId);
        return GroupResponse.from(group);
    }

    @Transactional
    public List<GroupResponse> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return GroupResponse.listFrom(groups);
    }

    @Transactional
    public void deleteGroupById(Long groupId, User user) {
        Group authorizedGroup = findGroupIfAuthorized(groupId, user.getMsrl());
        groupRepository.delete(authorizedGroup);
        groupEnrollmentRepository.deleteAllByGroupId(groupId);
    }

    private void enrollGroupWithMasterUser(User user, Group group) {
        GroupEnrollment groupEnrollment = GroupEnrollment.builder()
                .group(group)
                .user(user)
                .isMaster(true)
                .build();
        groupEnrollmentRepository.save(groupEnrollment);
    }

    private Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);
    }

    private Group findGroupIfAuthorized(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        if (!groupEnrollmentRepository.checkIfMasterOfGroup(userId, groupId)) {
            throw new GroupNotAuthorizedException();
        }

        return group;
    }
}
