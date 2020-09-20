package com.together.smwu.domain.room.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Master {
    private String name;
    private String profileImage;

    @QueryProjection
    public Master(String name, String profileImage){
        this.name = name;
        this.profileImage = profileImage;
    }
}
