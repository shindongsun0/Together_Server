package com.together.smwu.domain.room.application;

import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomEnrollment.application.RoomEnrollmentService;
import com.together.smwu.domain.roomEnrollment.dao.RoomEnrollmentRepository;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.roomEnrollment.exception.RoomUserMismatchException;
import com.together.smwu.domain.user.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomEnrollmentRepository roomEnrollmentRepository;
    private final RoomEnrollmentService roomEnrollmentService;

    public RoomServiceImpl(RoomRepository roomRepository, RoomEnrollmentRepository roomEnrollmentRepository, RoomEnrollmentService roomEnrollmentService) {
        this.roomRepository = roomRepository;
        this.roomEnrollmentRepository = roomEnrollmentRepository;
        this.roomEnrollmentService = roomEnrollmentService;
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
        List<Room> rooms = roomRepository.getAllRooms();
        return RoomResponse.listFrom(rooms);
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
        return RoomResponse.listFrom(rooms);
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
