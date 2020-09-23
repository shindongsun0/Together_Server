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

    public PlaceUpdateRequest() {
    }

    public PlaceUpdateRequest(String name, Integer category, Long mapX, Long mapY, String content, String location) {
        this.name = name;
        this.category = category;
        this.mapX = mapX;
        this.mapY = mapY;
        this.content = content;
        this.location = location;
    }

    public Place toPlaceEntity() {
        return Place.builder()
                .name(name)
                .category(category)
                .mapX(mapX)
                .mapY(mapY)
                .content(content)
                .location(location)
                .build();
    }
}
