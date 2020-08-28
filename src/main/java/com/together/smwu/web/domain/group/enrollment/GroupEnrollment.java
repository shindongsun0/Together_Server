package com.together.smwu.web.domain.group.enrollment;

import com.together.smwu.web.domain.group.Group;
import com.together.smwu.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "date", nullable = false)
    Timestamp date;

    @Column(name = "is_master", nullable = false)
    Boolean isMaster;

    @Builder
    public GroupEnrollment(Group group, User user, Timestamp date){
        this.group = group;
        this.user = user;
        this.date = date;
    }
}
