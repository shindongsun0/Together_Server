package com.together.smwu.web.repository.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

    Optional<Place> findByPlaceId(long id);
}
