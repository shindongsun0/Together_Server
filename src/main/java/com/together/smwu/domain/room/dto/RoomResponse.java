package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class RoomResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Timestamp createdTime;

    private RoomResponse() {
    }

    private RoomResponse(Long id, String title, String content, String imageUrl,
                         Timestamp createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdTime = createdTime;
    }

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getImageUrl(),
                room.getCreatedTime());
    }

    public static List<RoomResponse> listFrom(List<Room> rooms) {
        return rooms.stream()
                .map(RoomResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public Timestamp getCreatedTime(){
        return createdTime;
    }
}
