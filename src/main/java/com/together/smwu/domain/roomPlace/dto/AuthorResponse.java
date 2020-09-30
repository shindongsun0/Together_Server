package com.together.smwu.domain.roomPlace.dto;

import com.together.smwu.domain.user.domain.User;

public class AuthorResponse {
    private Long id;
    private String name;
    private String profileImage;

    private AuthorResponse() {
    }

    public AuthorResponse(Long id, String name, String profileImage) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
    }

    public static AuthorResponse of(User user) {
        return new AuthorResponse(user.getUserId(), user.getName(), user.getProfileImage());
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getProfileImage() {
        return this.profileImage;
    }
}
