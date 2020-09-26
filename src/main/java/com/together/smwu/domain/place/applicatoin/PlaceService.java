package com.together.smwu.domain.place.applicatoin;

import com.together.smwu.domain.place.dto.PlaceCreateRequest;
import com.together.smwu.domain.place.dto.PlaceResponse;
import com.together.smwu.domain.place.dto.PlaceUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PlaceService {
    Long create(PlaceCreateRequest request);

    void updatePlace(PlaceUpdateRequest request);

    void updatePlaceMainImage(MultipartFile multipartFile, Long placeId) throws IOException;

    PlaceResponse findByPlaceId(Long placeId);

    List<PlaceResponse> findAllPlaces();

    List<PlaceResponse> findByPlaceName(String name);
}
