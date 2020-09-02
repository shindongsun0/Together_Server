package com.together.smwu.domain.groupEnrollment.domain;

import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "GROUP_ENROLLMENT")
public class GroupEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_enrollment_id")
    private long groupEnrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_msrl", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @CreationTimestamp
    @Column(name = "enrolled_at", nullable = false)
    Timestamp enrolledAt;

    @Column(name = "is_master", nullable = false)
    Boolean isMaster;

    @Builder
    public GroupEnrollment(Group group, User user, Boolean isMaster){
        this.group = group;
        this.user = user;
        this.isMaster = isMaster;
    }
}
