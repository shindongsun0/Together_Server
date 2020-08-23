package com.together.smwu.web.service.group.enrollment;

import com.together.smwu.web.domain.group.Group;
import com.together.smwu.web.domain.group.enrollment.GroupEnrollment;
import com.together.smwu.web.domain.user.User;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentRequestDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentResponseDto;
import com.together.smwu.web.repository.group.GroupRepository;
import com.together.smwu.web.repository.group.enrollment.GroupEnrollmentRepository;
import com.together.smwu.web.repository.user.UserRepository;
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
     * group에 참여한다.
     *
     * @param requestDto group, user, credential
     * @return true: 등록성공
     */
    @Transactional
    public boolean enroll(GroupEnrollmentRequestDto requestDto) {

        // Validate that the group is an existing group
        if (null == requestDto.getGroup()) {
            throw new IllegalArgumentException("A group that does not exist.");
        }
        // Validate that the user is an existing group
        if (null == requestDto.getUser()) {
            throw new IllegalArgumentException("A user that does not exist.");
        }

        Group group = groupRepository.findById(requestDto.getGroup().getId())
                .orElseThrow(() -> new RuntimeException("No Corresponding group name found."));

        User user = userRepository.findById(requestDto.getUser().getMsrl())
                .orElseThrow(() -> new RuntimeException("No Corresponding user name found."));

        // check if is already exists.
        groupEnrollmentRepository
                .findByUserAndGroup(user, group)
                .orElseThrow(() -> new IllegalArgumentException("Already Existing Group And User"));

        GroupEnrollment groupEnrollment = requestDto.toEntity();

        groupEnrollmentRepository.save(groupEnrollment);

        return true;
    }

    /**
     * group을 삭제하거나 모두 강퇴시킨다.
     *
     * @param groupId 어떤 group
     * @param user 방장인지 확인하기 위함.
     */
    @Transactional
    public void deleteAllUsers(long groupId, User user) {

        //존재하는 group 인지 확인한다.
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("No Corresponding group found."));

        //방장(master) 인지 확인한다.
        GroupEnrollment groupEnrollment = groupEnrollmentRepository
                .findByUserAndGroup(user, groupRepository.getOne(groupId))
                .orElseThrow(() -> new RuntimeException("No Existing Group, User Information."));
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
                .orElseThrow(() -> new RuntimeException("No Corresponding group found."));

        GroupEnrollment groupEnrollment = groupEnrollmentRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new IllegalArgumentException("No Enrolled User in corresponding Group."));

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
                .orElseThrow(() -> new RuntimeException("No Corresponding group found."));

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
                .orElseThrow(() -> new RuntimeException("No Corresponding user found."));

        //find all groupEnrollments by user
        return groupEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(GroupEnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupEnrollmentResponseDto findById(long id){

        GroupEnrollment groupEnrollment = groupEnrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Enrollment Found."));

        return new GroupEnrollmentResponseDto(groupEnrollment);
    }

}
