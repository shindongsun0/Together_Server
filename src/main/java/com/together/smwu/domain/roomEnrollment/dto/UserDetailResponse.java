package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailResponse {
    private String name;
    private String profileImage;

    public UserDetailResponse(User user){
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
    }
}
