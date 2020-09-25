package com.together.smwu.domain.place.dao;

import com.together.smwu.domain.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    Optional<Place> findByNameAndMapX(String placeName, Long mapX);
}
