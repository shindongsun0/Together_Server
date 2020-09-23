package com.together.smwu.domain.roomPlace.application;

import com.together.smwu.domain.place.dao.PlaceRepository;
import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.place.exception.PlaceNotFoundException;
import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomPlace.dao.RoomPlaceRepository;
import com.together.smwu.domain.roomPlace.domain.RoomPlace;
import com.together.smwu.domain.roomPlace.dto.PlaceDetailResponse;
import com.together.smwu.domain.roomPlace.dto.RoomListResponse;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceRequest;
import com.together.smwu.domain.roomPlace.dto.RoomPlaceResponse;
import com.together.smwu.domain.roomPlace.exception.RoomPlaceNotFoundException;
import com.together.smwu.domain.user.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomPlaceServiceImpl implements RoomPlaceService {

    private final RoomPlaceRepository roomPlaceRepository;
    private final RoomRepository roomRepository;
    private final PlaceRepository placeRepository;

    public RoomPlaceServiceImpl(RoomPlaceRepository roomPlaceRepository, RoomRepository roomRepository, PlaceRepository placeRepository) {
        this.roomPlaceRepository = roomPlaceRepository;
        this.roomRepository = roomRepository;
        this.placeRepository = placeRepository;
    }

    @Transactional
    public Long save(RoomPlaceRequest request, User user) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(RoomNotFoundException::new);
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(PlaceNotFoundException::new);

        RoomPlace roomPlace = RoomPlace.builder()
                .room(room)
                .place(place)
                .build();
        roomPlaceRepository.save(roomPlace);
        return roomPlace.getId();
    }

    @Transactional
    public RoomListResponse findAllByPlaceId(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
        List<RoomPlace> roomPlaces = roomPlaceRepository.findAllByPlace(place);
        return RoomListResponse.from(roomPlaces);
    }

    @Transactional
    public List<PlaceDetailResponse> findAllByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        return roomPlaceRepository.findAllByRoom(room)
                .stream()
                .map(PlaceDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllPlacesByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        List<RoomPlace> roomPlaces = roomPlaceRepository.findAllByRoom(room);

        deleteRoomPlaceById(roomPlaces);
    }

    @Transactional
    public void deleteAllRoomsByPlaceId(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
        List<RoomPlace> roomPlaces = roomPlaceRepository.findAllByPlace(place);

        deleteRoomPlaceById(roomPlaces);
    }

    @Transactional
    public RoomPlaceResponse findByRoomPlaceId(Long roomPlaceId) {
        RoomPlace roomPlace = roomPlaceRepository.findById(roomPlaceId)
                .orElseThrow(RoomPlaceNotFoundException::new);
        return new RoomPlaceResponse(roomPlace);
    }

    private void deleteRoomPlaceById(List<RoomPlace> roomPlaces) {
        for (RoomPlace roomPlace : roomPlaces) {
            roomPlaceRepository.deleteById(roomPlace.getId());
        }
    }

}
