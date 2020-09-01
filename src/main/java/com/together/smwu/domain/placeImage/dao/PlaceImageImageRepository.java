package com.together.smwu.domain.placeImage.dao;

import com.together.smwu.domain.placeImage.domain.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceImageImageRepository extends JpaRepository<PlaceImage, Long>, PlaceImageRepositoryCustom {

    Optional<PlaceImage> findByPlaceImageId(long id);

}
