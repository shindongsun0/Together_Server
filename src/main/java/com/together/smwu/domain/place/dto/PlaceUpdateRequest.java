package com.together.smwu.domain.place.dto;

import com.together.smwu.domain.place.domain.Place;

import javax.validation.constraints.NotNull;

public class PlaceUpdateRequest {
    @NotNull
    private String name;
    private Integer category;
    private Long mapX;
    private Long mapY;
    private String content;
    private String location;
    private Long placeId;

    public PlaceUpdateRequest() {
    }

    public PlaceUpdateRequest(String name, Integer category, Long mapX, Long mapY, String content, String location,
                              Long placeId) {
        this.name = name;
        this.category = category;
        this.mapX = mapX;
        this.mapY = mapY;
        this.content = content;
        this.location = location;
        this.placeId = placeId;
    }

    public Place toPlaceEntity() {
        return Place.builder()
                .id(placeId)
                .name(name)
                .category(category)
                .mapX(mapX)
                .mapY(mapY)
                .content(content)
                .location(location)
                .build();
    }

    public @NotNull String getName() {
        return this.name;
    }

    public Integer getCategory() {
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

    public Long getPlaceId() {
        return this.placeId;
    }
}
