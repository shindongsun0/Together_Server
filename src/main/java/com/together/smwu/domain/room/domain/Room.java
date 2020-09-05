package com.together.smwu.domain.room.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "content")
    @Size(max = 255)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "credential")
    private String credential;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;

<<<<<<< Updated upstream:src/main/java/com/together/smwu/domain/group/domain/Group.java
=======
    public void update(Room room) {
        this.title = room.title;
        this.content = room.content;
        this.imageUrl = room.imageUrl;
        this.credential = room.credential;
    }

>>>>>>> Stashed changes:src/main/java/com/together/smwu/domain/room/domain/Room.java
    @Builder
    public Room(String title, String content, String imageUrl,
                String credential) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
    }
}
