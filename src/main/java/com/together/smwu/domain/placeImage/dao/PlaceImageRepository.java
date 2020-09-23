package com.together.smwu.domain.placeImage.dao;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.placeImage.domain.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long>, PlaceImageRepositoryCustom {

    List<PlaceImage> findByPlace(Place place);

}
