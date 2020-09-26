package com.together.smwu.domain.placeImage.dto;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.placeImage.domain.PlaceImage;

public class PlaceImageRequest {
    private Long placeId;
    private String imageUrl;

    public PlaceImageRequest() {
    }

    public PlaceImageRequest(Long placeId, String imageUrl) {
        this.placeId = placeId;
        this.imageUrl = imageUrl;
    }

    public Long getPlaceId() {
        return this.placeId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public PlaceImage toPlaceImageEntity(Place place) {
        return PlaceImage.builder()
                .imageUrl(imageUrl)
                .place(place)
                .build();
    }
}
