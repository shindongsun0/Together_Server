package com.together.smwu.domain.room.application;

import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.RoomDetailInfo;
import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomEnrollment.dao.RoomEnrollmentRepository;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.roomEnrollment.exception.RoomUserMismatchException;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomEnrollmentRepository roomEnrollmentRepository;
    private final UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomEnrollmentRepository roomEnrollmentRepository,
                           UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomEnrollmentRepository = roomEnrollmentRepository;
        this.userRepository = userRepository;
    }

    public Long create(RoomRequest request, User user) {
        final Room room = request.toRoomEntity();
        room.addTag(request.getTags());
        enrollRoomWithMasterUser(user, room);
        roomRepository.save(room);
        return room.getId();
    }

    @Transactional
    public void update(Long roomId, RoomRequest request, User user) {
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        room.addTag(request.getTags());
        room.update(request.toRoomEntity());
        roomRepository.save(room);
    }

    @Transactional
    public List<RoomResponse> findByTitle(String roomTitle, User user) {
        List<Room> rooms = roomRepository.findByTitle(roomTitle);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException(roomTitle);
        }
        List<RoomDetailInfo> roomDetailInfos = rooms.stream()
                .map(roomEnrollmentRepository::getMasterUser)
                .collect(Collectors.toList());

        return RoomResponse.listFrom(roomDetailInfos);
    }

    @Transactional
    public RoomResponse findByRoomId(Long roomId, User user) {
        Room room = findById(roomId);
        RoomDetailInfo roomDetailInfo = roomEnrollmentRepository.getMasterUser(room);
        return RoomResponse.from(roomDetailInfo);
    }

    @Transactional
    public List<RoomResponse> findAllRooms() {
        List<Room> rooms = roomRepository.getAllRooms();
        List<RoomDetailInfo> roomDetailInfos = getRoomDetailInfos(rooms);
        return RoomResponse.listFrom(roomDetailInfos);
    }

    private List<RoomDetailInfo> getRoomDetailInfos(List<Room> rooms) {
        List<RoomDetailInfo> roomDetailInfos = new java.util.ArrayList<>(Collections.emptyList());
        for(Room room : rooms){
                RoomDetailInfo addR = roomEnrollmentRepository.getMasterUser(room);
            roomDetailInfos.add(addR);
        }
        return roomDetailInfos;
    }

    @Transactional
    public void deleteRoomById(Long roomId, User user) {
        Room authorizedRoom = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findByUserAndRoom(user.getUserId(), roomId)
                .orElseThrow(RoomUserMismatchException::new);
        if (roomEnrollment.getIsMaster()) {
            roomRepository.deleteById(authorizedRoom.getId());
        }
    }

    @Transactional
    public List<RoomResponse> findByTagName(String tagName) {
        List<Room> rooms = roomRepository.findByTagName(tagName);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException(tagName);
        }
        List<RoomDetailInfo> roomDetailInfos = rooms.stream()
                .map(roomEnrollmentRepository::getMasterUser)
                .collect(Collectors.toList());

        return RoomResponse.listFrom(roomDetailInfos);
    }

    private void enrollRoomWithMasterUser(User user, Room room) {
        RoomEnrollment roomEnrollment = RoomEnrollment.builder()
                .room(room)
                .user(user)
                .isMaster(true)
                .build();
        room.getRoomEnrollments().add(roomEnrollment);
    }

    private Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
    }
}
