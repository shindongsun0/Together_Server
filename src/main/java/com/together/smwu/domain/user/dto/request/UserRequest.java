package com.together.smwu.domain.user.dto.request;

import com.together.smwu.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRequest {

    private Long userId;
    private String socialId;
    private String name;
    private String provider;
    private String profileImage;

    private UserRequest(Long userId, String socialId, String name,
                        String provider, String profileImage){
        this.userId = userId;
        this.socialId = socialId;
        this.name = name;
        this.provider = provider;
        this.profileImage = profileImage;
    }

    public User toUserEntity(){
        return User.builder()
                .userId(userId)
                .socialId(socialId)
                .name(name)
                .provider(provider)
                .profileImage(profileImage)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class Modify {
        private String name;
    }

}
