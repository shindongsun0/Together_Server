package com.together.smwu.domain.user.dto.request;

import com.together.smwu.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRequest {

    private String password;
    private String name;
    private String profileImage;

    private UserRequest(String password, String name,
                        String profileImage){
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
    }

    public User toUserEntity(){
        return User.builder()
                .password(password)
                .name(name)
                .profileImage(profileImage)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class Modify {
        private String name;
    }

}
