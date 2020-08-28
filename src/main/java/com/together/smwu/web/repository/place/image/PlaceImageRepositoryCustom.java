package com.together.smwu.web.repository.place.image;

import com.together.smwu.web.repository.place.Place;

import java.util.List;

public interface PlaceImageRepositoryCustom {
    List<PlaceImage> findAllByPlace(Place place);
}
