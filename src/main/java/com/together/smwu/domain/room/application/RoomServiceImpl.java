package com.together.smwu.domain.room.application;

import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomEnrollment.dao.RoomEnrollmentRepository;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.roomEnrollment.exception.RoomNotAuthorizedException;
import com.together.smwu.domain.user.domain.User;
import org.springframework.stereotype.Service;

import javax.jdo.annotations.Transactional;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomEnrollmentRepository roomEnrollmentRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomEnrollmentRepository roomEnrollmentRepository) {
        this.roomRepository = roomRepository;
        this.roomEnrollmentRepository = roomEnrollmentRepository;
    }

    public Long create(RoomRequest request, User user) {
        Room room = roomRepository.save(request.toRoomEntity());
        enrollRoomWithMasterUser(user, room);
        return room.getId();
    }

    /*
    - findAllRooms(): 모든 room조회 [/all]
    - deleteRoomById(Long id): room삭제 → roomEnrollment의 deleteRoom호출
     */
    @Transactional
    public void update(Long roomId, RoomRequest request, User user) {
        Room room = findRoomIfAuthorized(roomId, user.getUserId());
        room.update(request.toRoomEntity());
        roomRepository.save(room);
    }

    @Transactional
    public List<RoomResponse> findByTitle(String roomTitle) {
        List<Room> rooms = roomRepository.findByTitle(roomTitle);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException(roomTitle);
        }
        return RoomResponse.listFrom(rooms);
    }

    @Transactional
    public RoomResponse findByRoomId(Long roomId) {
        Room room = findById(roomId);
        return RoomResponse.from(room);
    }

    @Transactional
    public List<RoomResponse> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return RoomResponse.listFrom(rooms);
    }

    @Transactional
    public void deleteRoomById(Long roomId, User user) {
        roomEnrollmentRepository.deleteAllByRoomId(roomId);
        Room authorizedRoom = findRoomIfAuthorized(roomId, user.getUserId());
        roomRepository.delete(authorizedRoom);
    }

    private void enrollRoomWithMasterUser(User user, Room room) {
        RoomEnrollment roomEnrollment = RoomEnrollment.builder()
                .room(room)
                .user(user)
                .isMaster(true)
                .build();
        roomEnrollmentRepository.save(roomEnrollment);
    }

    private Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
    }

    private Room findRoomIfAuthorized(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        if (!roomEnrollmentRepository.checkIsMasterOfRoom(userId, roomId)) {
            throw new RoomNotAuthorizedException();
        }

        return room;
    }
}
