package com.together.smwu.web.repository.place.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceImageImageRepository extends JpaRepository<PlaceImage, Long>, PlaceImageRepositoryCustom {

    Optional<PlaceImage> findByPlaceImageId(long id);

}
