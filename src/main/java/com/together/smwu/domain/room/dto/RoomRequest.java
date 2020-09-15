package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RoomRequest {
    private final String BASE_IMAGE_URL = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/up.png";

    @NotNull
    private String title;
    private String content;
    private String imageUrl;
    private String credential;
    private List<String> tags;

    private RoomRequest() {
    }

    private RoomRequest(String title, String content, String imageUrl,
                        String credential, List<String> tags) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
        this.tags = tags;
    }

    public Room toRoomEntity() {
        if (null == imageUrl) {
            imageUrl = BASE_IMAGE_URL;
        }
        return Room.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .credential(credential)
                .build();
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

    public List<String> getTags() {
        return tags;
    }
}
