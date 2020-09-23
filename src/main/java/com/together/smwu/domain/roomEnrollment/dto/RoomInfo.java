package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfo {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Timestamp createdTime;
    private List<String> tags;

    public RoomInfo(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.content = room.getContent();
        this.imageUrl = room.getImageUrl();
        this.createdTime = room.getCreatedTime();
        this.tags = room.getTagNames();
    }
}
