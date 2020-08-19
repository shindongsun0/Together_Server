package com.together.smwu.web.service.group.enrollment;

import com.together.smwu.web.repository.group.enrollment.GroupEnrollmentRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupEnrollmentService {

    private final GroupEnrollmentRepository groupEnrollmentRepository;

    public GroupEnrollmentService(GroupEnrollmentRepository groupEnrollmentRepository) {
        this.groupEnrollmentRepository = groupEnrollmentRepository;
    }

//    @Transactional
//    public String save(GroupEnrollmentRequestDto requestDto, ){
//
//        // Validate that the group is an existing group
//        if(null == requestDto.getGroup()){
//            throw new IllegalArgumentException("A group that does not exist.");
//        }
//        else if(requestDto.getCredential())
//    }


}
