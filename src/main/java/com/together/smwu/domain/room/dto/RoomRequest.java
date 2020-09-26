package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RoomRequest {
    private final String BASE_IMAGE_URL = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/up.png";

    @NotNull
    private String title;
    private String content;
    private MultipartFile imageUrl;
    private String credential;
    private List<String> tags;

    private RoomRequest() {
    }

    private RoomRequest(String title, String content, MultipartFile imageUrl,
                        String credential, List<String> tags) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
        this.tags = tags;
    }

    public Room toRoomEntity(String roomImageUrl) {
        return Room.builder()
                .title(title)
                .content(content)
                .imageUrl(roomImageUrl)
                .credential(credential)
                .build();
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public MultipartFile getImageUrl(){
        return imageUrl;
    }

    public List<String> getTags() {
        return tags;
    }
}
