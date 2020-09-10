package com.together.smwu.domain.user.dto.response;

import com.together.smwu.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String socialId;
    private String password;
    private String name;
    private String provider;
    private String profileImage;

    private UserResponse(Long userId, String socialId, String password, String name,
                         String provider, String profileImage) {
        this.userId = userId;
        this.socialId = socialId;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.profileImage = profileImage;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .socialId(user.getSocialId())
                .password(user.getPassword())
                .name(user.getName())
                .provider(user.getProvider())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static List<UserResponse> listFrom(List<User> users){
        return users.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
}
