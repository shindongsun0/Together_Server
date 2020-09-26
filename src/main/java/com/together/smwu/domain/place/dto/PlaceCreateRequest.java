package com.together.smwu.domain.place.dto;

import com.together.smwu.domain.place.domain.Place;

import javax.validation.constraints.NotNull;

public class PlaceCreateRequest {
    private final String BASE_PLACE_IMAGE_URL = ""; //TODO S3에 추가

    @NotNull
    private String name;
    private String category;
    private Long mapX;
    private Long mapY;
    private String content;
    private String location;
    private String mainImageUrl;
    private Long roomId;

    public PlaceCreateRequest() {
    }

    public PlaceCreateRequest(String name, String category, Long mapX, Long mapY,
                              String content, String location, String mainImageUrl,
                              Long roomId) {
        this.name = name;
        this.category = category;
        this.mapX = mapX;
        this.mapY = mapY;
        this.content = content;
        this.location = location;
        this.mainImageUrl = mainImageUrl;
        this.roomId = roomId;
    }

    public Place toPlaceEntity() {
        if (null == mainImageUrl) {
            mainImageUrl = BASE_PLACE_IMAGE_URL;
        }
        return Place.builder()
                .name(name)
                .category(category)
                .mapX(mapX)
                .mapY(mapY)
                .content(content)
                .location(location)
                .mainImageUrl(mainImageUrl)
                .build();
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public Long getMapX() {
        return this.mapX;
    }

    public Long getMapY() {
        return this.mapY;
    }

    public String getContent() {
        return this.content;
    }

    public String getLocation() {
        return this.location;
    }

    public String getMainImageUrl() {
        return this.mainImageUrl;
    }

    public Long getRoomId() {
        return this.roomId;
    }
}
