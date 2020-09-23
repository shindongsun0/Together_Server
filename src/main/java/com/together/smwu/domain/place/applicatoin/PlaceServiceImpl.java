package com.together.smwu.domain.place.applicatoin;

import com.together.smwu.domain.place.dao.PlaceRepository;
import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.place.dto.PlaceCreateRequest;
import com.together.smwu.domain.place.dto.PlaceResponse;
import com.together.smwu.domain.place.dto.PlaceUpdateRequest;
import com.together.smwu.domain.place.exception.PlaceNotFoundException;
import com.together.smwu.domain.placeImage.dao.PlaceImageRepository;
import com.together.smwu.domain.placeImage.domain.PlaceImage;
import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomPlace.dao.RoomPlaceRepository;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;
import com.together.smwu.global.aws.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final S3Uploader s3Uploader;
    private final PlaceImageRepository placeImageRepository;
    private final RoomRepository roomRepository;
    private final RoomPlaceRepository roomPlaceRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository, S3Uploader s3Uploader, PlaceImageRepository placeImageRepository, RoomRepository roomRepository, RoomPlaceRepository roomPlaceRepository) {
        this.placeRepository = placeRepository;
        this.s3Uploader = s3Uploader;
        this.placeImageRepository = placeImageRepository;
        this.roomRepository = roomRepository;
        this.roomPlaceRepository = roomPlaceRepository;
    }

    @Transactional
    public Long create(PlaceCreateRequest request) {
        Place place = request.toPlaceEntity();
        placeRepository.save(place);
        savePlaceMainImage(place);
        enrollPlaceInRoom(request, place);
        return place.getId();
    }

    private void enrollPlaceInRoom(PlaceCreateRequest request, Place place) {
        Room room = getRoomByRoomId(request.getRoomId());
        RoomPlace enrolledPlace = RoomPlace.builder()
                .room(room)
                .place(place)
                .build();
        roomPlaceRepository.save(enrolledPlace);
    }

    private void savePlaceMainImage(Place place) {
        PlaceImage placeMainImage = PlaceImage.builder()
                .place(place)
                .imageUrl(place.getMainImageUrl())
                .build();
        placeImageRepository.save(placeMainImage);
    }

    @Transactional
    public void updatePlace(PlaceUpdateRequest request) {
        Place place = request.toPlaceEntity();
        placeRepository.save(place);
    }

    @Transactional
    public void updatePlaceMainImage(MultipartFile multipartFile, Long placeId) throws IOException {
        Place place = getPlaceByPlaceId(placeId);
        String fileName = "static/place/" + placeId;
        String uploadedPlaceUrl = s3Uploader.upload(multipartFile, fileName);
        place.updateMainImageUrl(uploadedPlaceUrl);
        placeRepository.save(place);
    }

    @Transactional
    public PlaceResponse findByPlaceId(Long placeId) {
        Place place = getPlaceByPlaceId(placeId);
        return new PlaceResponse(place);
    }

    @Transactional
    public List<PlaceResponse> findAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return PlaceResponse.listFrom(places);
    }

    @Transactional
    public List<PlaceResponse> findByPlaceName(String name) {
        List<Place> places = placeRepository.findPlaceNameContains(name);
        return PlaceResponse.listFrom(places);
    }

    private Room getRoomByRoomId(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
    }

    private Place getPlaceByPlaceId(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }
}
