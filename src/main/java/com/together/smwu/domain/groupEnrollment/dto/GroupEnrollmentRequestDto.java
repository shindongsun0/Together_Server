package com.together.smwu.domain.groupEnrollment.dto;

import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.groupEnrollment.domain.GroupEnrollment;
import com.together.smwu.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class GroupEnrollmentRequestDto {

    private Group group;
    private User user;

    @Builder
    public GroupEnrollmentRequestDto(Group group, User user, String credential) {
        this.group = group;
        this.user = user;
    }

    public GroupEnrollment toEntity() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return GroupEnrollment.builder()
                .group(group)
                .user(user)
                .date(now)
                .build();
    }
}
