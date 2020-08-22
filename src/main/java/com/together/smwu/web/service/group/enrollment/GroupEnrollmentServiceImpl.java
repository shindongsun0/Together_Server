package com.together.smwu.web.service.group.enrollment;

import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentListResponseDto;
import com.together.smwu.web.dto.group.enrollment.GroupEnrollmentRequestDto;
import com.together.smwu.web.repository.group.Group;
import com.together.smwu.web.repository.group.GroupRepository;
import com.together.smwu.web.repository.group.enrollment.GroupEnrollment;
import com.together.smwu.web.repository.group.enrollment.GroupEnrollmentRepository;
import com.together.smwu.web.repository.user.User;
import com.together.smwu.web.repository.user.UserJpaRepo;
import org.springframework.stereotype.Service;

import javax.jdo.annotations.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupEnrollmentServiceImpl implements GroupEnrollmentService {

    private final GroupEnrollmentRepository groupEnrollmentRepository;
    private final GroupRepository groupRepository;
    private final UserJpaRepo userJpaRepo;

    public GroupEnrollmentServiceImpl(GroupEnrollmentRepository groupEnrollmentRepository,
                                  GroupRepository groupRepository, UserJpaRepo userJpaRepo) {
        this.groupEnrollmentRepository = groupEnrollmentRepository;
        this.groupRepository = groupRepository;
        this.userJpaRepo = userJpaRepo;
    }

    /**
     * @param requestDto group, user, credential
     * @return success or error
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

        User user = userJpaRepo.findById(requestDto.getUser().getMsrl())
                .orElseThrow(() -> new RuntimeException("No Corresponding user name found."));

        Optional<GroupEnrollment> groupOptional = groupEnrollmentRepository
                .findByUserAndGroup(user, group);

        if (groupOptional.isPresent()) {
            throw new IllegalArgumentException("Already Existing Group And User");
        }

//        if(groupOptional.get().getGroup().getCredential() != null){
//            groupOptional.get(). TODO 이걸 여기서 처리해야되나 고민중
//        }

        GroupEnrollment groupEnrollment = requestDto.toEntity();

        groupEnrollmentRepository.save(groupEnrollment);

        return true;
    }

    @Transactional
    public void deleteAllUsers(long groupId, User user) {

        //존재하는 group 인지 확인한다.
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("No Corresponding group found."));

        //방장(master) 인지 확인한다.
        GroupEnrollment groupEnrollment = groupEnrollmentRepository.findByUserAndGroup(user, groupRepository.getOne(groupId))
                .orElseThrow(() -> new RuntimeException("No Existing Group, User Information."));
        if (groupEnrollment.getIsMaster()) {
            groupEnrollmentRepository.deleteAllByGroupId(groupId);
        } else {
            throw new IllegalArgumentException("The user does not have permission to delete the group.");
        }

    }

    @Transactional
    public void deleteUserFromGroup(long userId) {

        userJpaRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Corresponding user found."));

        groupEnrollmentRepository.deleteAllByUserId(userId);
    }


    /**
     * @param groupId
     * @return 해당 그룹의 전체 enroll 정보를 가져온다.
     */
    @Transactional
    public List<GroupEnrollmentListResponseDto> findAllByGroup(long groupId) {

        //group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("No Corresponding group found."));

        //find all groupEnrollments by group
        return groupEnrollmentRepository.findAllByGroup(group)
                .stream()
                .map(GroupEnrollmentListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GroupEnrollmentListResponseDto> findAllByUser(long userId) {

        //user
        User user = userJpaRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Corresponding user found."));

        //find all groupEnrollments by user
        return groupEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(GroupEnrollmentListResponseDto::new)
                .collect(Collectors.toList());
    }

}
