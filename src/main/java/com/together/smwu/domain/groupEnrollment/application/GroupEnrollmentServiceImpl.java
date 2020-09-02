package com.together.smwu.domain.groupEnrollment.application;

import com.together.smwu.domain.group.dao.GroupRepository;
import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.group.exception.GroupNotFoundException;
import com.together.smwu.domain.groupEnrollment.dao.GroupEnrollmentRepository;
import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentRequestDto;
import com.together.smwu.domain.groupEnrollment.dto.GroupEnrollmentResponseDto;
import com.together.smwu.domain.groupEnrollment.exception.GroupUserMismatchException;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.jdo.annotations.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupEnrollmentServiceImpl implements GroupEnrollmentService {

    private final GroupEnrollmentRepository groupEnrollmentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupEnrollmentServiceImpl(GroupEnrollmentRepository groupEnrollmentRepository,
                                      GroupRepository groupRepository, UserRepository userRepository) {
        this.groupEnrollmentRepository = groupEnrollmentRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    /**
     * group에 참여한다. 이 메소드는 방장이 아닌 새 User가 그룹에 참여할 때 불린다.
     *
     * @param requestDto group, user, credential
     * @return true: 등록성공
     */
    @Transactional
    public Long enroll(GroupEnrollmentRequestDto requestDto, User user) {

        GroupEnrollment groupEnrollment = convertToGroupEnrollment(requestDto, user);

        // check if is already exists.
        groupEnrollmentRepository
                .findByUserAndGroup(groupEnrollment.getUser(), groupEnrollment.getGroup())
                .orElseThrow(GroupUserMismatchException::new);

        groupEnrollmentRepository.save(groupEnrollment);

        return groupEnrollment.getGroupEnrollmentId();
    }

    private GroupEnrollment convertToGroupEnrollment(GroupEnrollmentRequestDto request, User user) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(GroupNotFoundException::new);

        boolean isMasterUser = checkIsMasterUser(group, user);

        return GroupEnrollment.builder()
                .group(group)
                .user(user)
                .isMaster(isMasterUser)
                .build();
    }

    private boolean checkIsMasterUser(Group group, User user) {
        return groupEnrollmentRepository.findAllByGroup(group).isEmpty();
    }

    /**
     * group을 삭제하거나 모두 강퇴시킨다.
     *
     * @param groupId 어떤 group
     * @param user    방장인지 확인하기 위함.
     */
    @Transactional
    public void deleteAllUsers(long groupId, User user) {

        //존재하는 group 인지 확인한다.
        groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        //방장(master) 인지 확인한다.
        GroupEnrollment groupEnrollment = groupEnrollmentRepository
                .findByUserAndGroup(user, groupRepository.getOne(groupId))
                .orElseThrow(GroupUserMismatchException::new);

        if (groupEnrollment.getIsMaster()) {
            groupEnrollmentRepository.deleteAllByGroupId(groupId);
        } else {
            throw new IllegalArgumentException("The user does not have permission to delete the group.");
        }

    }

    /**
     * group에서 user가 나갈 때 user를 삭제한다.
     *
     * @param groupId 어떤 group
     * @param user
     */
    @Transactional
    public void deleteUserFromGroup(long groupId, User user) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        GroupEnrollment groupEnrollment = groupEnrollmentRepository.findByUserAndGroup(user, group)
                .orElseThrow(GroupUserMismatchException::new);

        long groupEnrollmentId = groupEnrollment.getGroupEnrollmentId();

        groupEnrollmentRepository.deleteById(groupEnrollmentId);
    }


    /**
     * group에 속한 모든 user를 알 수 있다.
     *
     * @param groupId
     * @return 해당 그룹의 전체 enroll 정보를 가져온다.
     */
    @Transactional
    public List<GroupEnrollmentResponseDto> findAllByGroup(long groupId) {

        //group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        //find all groupEnrollments by group
        return groupEnrollmentRepository.findAllByGroup(group)
                .stream()
                .map(GroupEnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * user가 속한 모든 group을 알 수 있다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public List<GroupEnrollmentResponseDto> findAllByUser(long userId) {

        //user
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        //find all groupEnrollments by user
        return groupEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(GroupEnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupEnrollmentResponseDto findById(long id) {

        GroupEnrollment groupEnrollment = groupEnrollmentRepository.findById(id)
                .orElseThrow(GroupUserMismatchException::new);

        return new GroupEnrollmentResponseDto(groupEnrollment);
    }

}
