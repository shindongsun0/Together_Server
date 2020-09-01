package com.together.smwu.domain.placeImage.dao;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.placeImage.domain.PlaceImage;

import java.util.List;

public interface PlaceImageRepositoryCustom {
    List<PlaceImage> findAllByPlace(Place place);
}
