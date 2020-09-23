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
    private List<String> tags;
    private Master master;

    private RoomResponse() {
    }

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.content = room.getContent();
        this.imageUrl = room.getImageUrl();
        this.createdTime = room.getCreatedTime();
        this.tags = room.getTagNames();
    }

    private RoomResponse(Long id, String title, String content, String imageUrl,
                         Timestamp createdTime, List<String> tags, Master masterUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdTime = createdTime;
        this.tags = tags;
        this.master = masterUser;
    }

    public static RoomResponse from(RoomDetailInfo roomDetailInfo) {
        Room room = roomDetailInfo.getRoom();
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getImageUrl(),
                room.getCreatedTime(),
                room.getTagNames(),
                roomDetailInfo.getMaster());

    }

    public static List<RoomResponse> listFrom(List<RoomDetailInfo> roomDetailInfos) {
        return roomDetailInfos.stream()
                .map(RoomResponse::from)
                .collect(Collectors.toList());
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

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public Master getMaster() {
        return master;
    }
}
