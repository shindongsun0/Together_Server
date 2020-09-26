package com.together.smwu.domain.placeImage.appplicatoin;

import com.together.smwu.domain.place.dao.PlaceRepository;
import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.place.exception.PlaceNotFoundException;
import com.together.smwu.domain.placeImage.dao.PlaceImageRepository;
import com.together.smwu.domain.placeImage.domain.PlaceImage;
import com.together.smwu.domain.placeImage.dto.PlaceImageRequest;
import com.together.smwu.domain.placeImage.dto.PlaceImageResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlaceImageServiceImpl implements PlaceImageService {
    private final PlaceRepository placeRepository;
    private final PlaceImageRepository placeImageRepository;

    public PlaceImageServiceImpl(PlaceRepository placeRepository, PlaceImageRepository placeImageRepository) {
        this.placeRepository = placeRepository;
        this.placeImageRepository = placeImageRepository;
    }

    @Transactional
    public Long addPlaceImage(PlaceImageRequest request) {
        Place place = getPlaceByPlaceId(request.getPlaceId());
        PlaceImage placeImage = request.toPlaceImageEntity(place);
        placeImageRepository.save(placeImage);
        return placeImage.getId();
    }

    @Transactional
    public List<PlaceImageResponse> findByPlaceId(Long placeId) {
        Place place = getPlaceByPlaceId(placeId);
        List<PlaceImage> placeImages = placeImageRepository.findByPlace(place);
        return PlaceImageResponse.listFrom(placeImages);
    }

    @Transactional
    public void update(PlaceImageRequest request) {
        Place place = getPlaceByPlaceId(request.getPlaceId());
        PlaceImage placeImage = request.toPlaceImageEntity(place);
        placeImageRepository.save(placeImage);
    }

    private Place getPlaceByPlaceId(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }
}
