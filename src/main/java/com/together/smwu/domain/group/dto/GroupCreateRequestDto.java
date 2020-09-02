package com.together.smwu.domain.group.dto;

import com.together.smwu.domain.group.domain.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GroupCreateRequestDto {

    private String title;
    private String content;
    private String imageUrl;
    private String credential;


    private GroupCreateRequestDto(String title, String content, String imageUrl,
                                  String credential) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
    }

    public Group toGroupEntity() {
        return Group.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .credential(credential)
                .build();
    }
}
