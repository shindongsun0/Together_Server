package com.together.smwu.domain.placeImage.appplicatoin;

import com.together.smwu.domain.placeImage.dto.PlaceImageRequest;
import com.together.smwu.domain.placeImage.dto.PlaceImageResponse;

import java.util.List;

public interface PlaceImageService {
    Long addPlaceImage(PlaceImageRequest request);

    List<PlaceImageResponse> findByPlaceId(Long placeId);

    void update(PlaceImageRequest request);
}
