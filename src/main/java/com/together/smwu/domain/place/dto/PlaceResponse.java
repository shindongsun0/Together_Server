package com.together.smwu.domain.place.dto;


import com.together.smwu.domain.place.domain.Place;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceResponse {
    private Long id;
    private String name;
    private String category;
    private Long mapX;
    private Long mapY;
    private String content;
    private String location;
    private String mainImageUrl;
    private Timestamp postTime;

    public PlaceResponse(){}

    @Builder
    public PlaceResponse(Long id, String name, String category, Long mapX,
                         Long mapY, String content, String location, String mainImageUrl,
                         Timestamp postTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.mapX = mapX;
        this.mapY = mapY;
        this.content = content;
        this.location = location;
        this.mainImageUrl = mainImageUrl;
        this.postTime = postTime;
    }

    public PlaceResponse(Place place){
        this.id = place.getId();
        this.name = place.getName();
        this.category = place.getCategory();
        this.mapX = place.getMapX();
        this.mapY = place.getMapY();
        this.content = place.getContent();
        this.location = place.getLocation();
        this.mainImageUrl = place.getMainImageUrl();
        this.postTime = place.getPostTime();
    }

    public static PlaceResponse from(Place place){
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory())
                .mapX(place.getMapX())
                .mapY(place.getMapY())
                .content(place.getContent())
                .location(place.getLocation())
                .mainImageUrl(place.getMainImageUrl())
                .postTime(place.getPostTime())
                .build();
    }

    public static List<PlaceResponse> listFrom(List<Place> places){
        return places.stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return this.id;
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

    public Timestamp getPostTime() {
        return this.postTime;
    }
}
