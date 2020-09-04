package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RoomRequest {
    private final String BASE_IMAGE_URL = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/up.png";

    private String title;
    private String content;
    private String imageUrl;
    private String credential;


    private RoomRequest(String title, String content, String imageUrl,
                        String credential) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
    }

    public Room toRoomEntity() {
        if (imageUrl.isEmpty()) {
            imageUrl = BASE_IMAGE_URL;
        }
        return Room.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .credential(credential)
                .build();
    }
}
