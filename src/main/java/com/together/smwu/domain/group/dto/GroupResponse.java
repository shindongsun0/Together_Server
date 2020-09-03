package com.together.smwu.domain.group.dto;

import com.together.smwu.domain.group.domain.Group;
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
public class GroupResponse {

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

    private GroupResponse(Long id, String title, String content, String imageUrl,
                          String credential, Timestamp createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.credential = credential;
        this.createdTime = createdTime;
    }

    public static GroupResponse from(Group group) {
        return GroupResponse.builder()
                .id(group.getId())
                .title(group.getTitle())
                .content(group.getContent())
                .imageUrl(group.getImageUrl())
                .credential(group.getCredential())
                .createdTime(group.getCreatedTime())
                .build();
    }

    public static List<GroupResponse> listFrom(List<Group> groups){
        return groups.stream()
                .map(GroupResponse::from)
                .collect(Collectors.toList());
    }
}
