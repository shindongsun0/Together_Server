package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
public class RoomResponse {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageUrl;

    private String credential;

    @NotNull
    private Timestamp createdTime;

    private RoomResponse(Long id, String title, String content, String imageUrl,
                         String credential, Timestamp createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
        this.createdTime = createdTime;
    }

    public static RoomResponse from(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .content(room.getContent())
                .imageUrl(room.getImageUrl())
                .credential(room.getCredential())
                .createdTime(room.getCreatedTime())
                .build();
    }

    public static List<RoomResponse> listFrom(List<Room> rooms){
        return rooms.stream()
                .map(RoomResponse::from)
                .collect(Collectors.toList());
    }
}
