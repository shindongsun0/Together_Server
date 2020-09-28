package com.together.smwu.domain.room.domain;

import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
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
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Tag> tags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RoomEnrollment> roomEnrollments = new ArrayList<>();

    public void update(Room room) {
        this.title = room.title;
        this.content = room.content;
        this.imageUrl = room.imageUrl;
        this.credential = room.credential;
    }

    @Builder
    public Room(String title, String content, String imageUrl,
                String credential) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
    }

    public void addTag(List<String> tags) {
        List<Tag> createdTags = tags.stream()
                .map(this::createTag)
                .collect(Collectors.toList());
        this.tags.clear();
        this.tags.addAll(createdTags);
    }

    private Tag createTag(String tag) {
        return Tag.builder()
                .name(tag)
                .room(this)
                .build();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCredential() {
        return credential;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public List<RoomEnrollment> getRoomEnrollments() {
        return roomEnrollments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getTagNames(){
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
