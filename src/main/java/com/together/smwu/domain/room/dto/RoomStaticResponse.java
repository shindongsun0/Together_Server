package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class RoomStaticResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Boolean hasCredential;
    private Timestamp createdTime;
    private List<String> tags;
    private Master master;

    private RoomStaticResponse() {
    }

    public RoomStaticResponse(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.content = room.getContent();
        this.imageUrl = room.getImageUrl();
        this.hasCredential = hasCredential(room.getCredential());
        this.createdTime = room.getCreatedTime();
        this.tags = room.getTagNames();
    }

    private Boolean hasCredential(String credential) {
        return null != credential;
    }

    private RoomStaticResponse(Long id, String title, String content, String imageUrl, String credential,
                         Timestamp createdTime, List<String> tags, Master masterUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.hasCredential = hasCredential(credential);
        this.createdTime = createdTime;
        this.tags = tags;
        this.master = masterUser;
    }

    public static RoomStaticResponse from(RoomWithMasterInfo roomWithMasterInfo) {
        Room room = roomWithMasterInfo.getRoom();
        return new RoomStaticResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getImageUrl(),
                room.getCredential(),
                room.getCreatedTime(),
                room.getTagNames(),
                roomWithMasterInfo.getMaster());
    }

    public static List<RoomStaticResponse> listFrom(List<RoomWithMasterInfo> roomWithMasterInfos) {
        return roomWithMasterInfos.stream()
                .map(RoomStaticResponse::from)
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

    public Boolean getHasCredential() {
        return hasCredential;
    }

    public List<String> getTags() {
        return tags;
    }

    public Master getMaster() {
        return master;
    }
}
