package com.together.smwu.web.dto.group.enrollment;

import com.together.smwu.web.repository.group.Group;
import com.together.smwu.web.repository.group.enrollment.GroupEnrollment;
import com.together.smwu.web.repository.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class GroupEnrollmentRequestDto {

    private Group group;
    private String credential;

    @Builder
    public GroupEnrollmentRequestDto(Group group, String credential) {
        this.group = group;
        this.credential = credential;
    }

    public GroupEnrollment toEntity(User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return GroupEnrollment.builder()
                .group(group)
                .user(user)
                .date(now)
                .build();
    }
}
