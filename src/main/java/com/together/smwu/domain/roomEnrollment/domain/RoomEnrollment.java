package com.together.smwu.domain.roomEnrollment.domain;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "ROOM_ENROLLMENT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "room_id"})}
)
public class RoomEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_enrollment_id")
    private long roomEnrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @CreationTimestamp
    @Column(name = "enrolled_at", nullable = false)
    Timestamp enrolledAt;

    @Column(name = "is_master", nullable = false)
    Boolean isMaster;

    @Builder
    public RoomEnrollment(Room room, User user, Boolean isMaster) {
        this.room = room;
        this.user = user;
        this.isMaster = isMaster;
    }
}
