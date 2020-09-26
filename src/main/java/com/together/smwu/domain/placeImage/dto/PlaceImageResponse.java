package com.together.smwu.domain.placeImage.dto;

import com.together.smwu.domain.placeImage.domain.PlaceImage;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceImageResponse {
    private String imageUrl;
    private Long placeId;

    public PlaceImageResponse(String imageUrl, Long placeId) {
        this.imageUrl = imageUrl;
        this.placeId = placeId;
    }

    public PlaceImageResponse() {
    }

    public static PlaceImageResponse from(PlaceImage placeImage) {
        return new PlaceImageResponse(
                placeImage.getImageUrl(),
                placeImage.getPlace().getId()
        );
    }

    public static List<PlaceImageResponse> listFrom(List<PlaceImage> placeImages) {
        return placeImages.stream()
                .map(PlaceImageResponse::from)
                .collect(Collectors.toList());
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Long getPlaceId() {
        return this.placeId;
    }
}
