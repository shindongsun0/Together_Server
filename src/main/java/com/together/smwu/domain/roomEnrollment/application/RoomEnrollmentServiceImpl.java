package com.together.smwu.domain.roomEnrollment.application;

import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomEnrollment.dao.RoomEnrollmentRepository;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequestDto;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponseDto;
import com.together.smwu.domain.roomEnrollment.exception.RoomUserMismatchException;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.jdo.annotations.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomEnrollmentServiceImpl implements RoomEnrollmentService {

    private final RoomEnrollmentRepository roomEnrollmentRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomEnrollmentServiceImpl(RoomEnrollmentRepository roomEnrollmentRepository,
                                     RoomRepository roomRepository, UserRepository userRepository) {
        this.roomEnrollmentRepository = roomEnrollmentRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    /**
     * room에 참여한다. 이 메소드는 방장이 아닌 새 User가 그룹에 참여할 때 불린다.
     *
     * @param requestDto room, user, credential
     * @return true: 등록성공
     */
    @Transactional
    public Long enroll(RoomEnrollmentRequestDto requestDto, User user) {

        RoomEnrollment roomEnrollment = convertToRoomEnrollment(requestDto, user);

        // check if is already exists.
        roomEnrollmentRepository
                .findByUserAndRoom(roomEnrollment.getUser(), roomEnrollment.getRoom())
                .orElseThrow(RoomUserMismatchException::new);

        roomEnrollmentRepository.save(roomEnrollment);

        return roomEnrollment.getRoomEnrollmentId();
    }

    private RoomEnrollment convertToRoomEnrollment(RoomEnrollmentRequestDto request, User user) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(RoomNotFoundException::new);

        boolean isMasterUser = checkIsMasterUser(room, user);

        return RoomEnrollment.builder()
                .room(room)
                .user(user)
                .isMaster(isMasterUser)
                .build();
    }

    private boolean checkIsMasterUser(Room room, User user) {
        return roomEnrollmentRepository.findAllByRoom(room).isEmpty();
    }

    /**
     * room을 삭제하거나 모두 강퇴시킨다.
     *
     * @param roomId 어떤 room
     * @param user    방장인지 확인하기 위함.
     */
    @Transactional
    public void deleteAllUsers(long roomId, User user) {

        //존재하는 room 인지 확인한다.
        roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        //방장(master) 인지 확인한다.
        RoomEnrollment roomEnrollment = roomEnrollmentRepository
                .findByUserAndRoom(user, roomRepository.getOne(roomId))
                .orElseThrow(RoomUserMismatchException::new);

        if (roomEnrollment.getIsMaster()) {
            roomEnrollmentRepository.deleteAllByRoomId(roomId);
        } else {
            throw new IllegalArgumentException("The user does not have permission to delete the room.");
        }

    }

    /**
     * room에서 user가 나갈 때 user를 삭제한다.
     *
     * @param roomId 어떤 room
     * @param user
     */
    @Transactional
    public void deleteUserFromRoom(long roomId, User user) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findByUserAndRoom(user, room)
                .orElseThrow(RoomUserMismatchException::new);

        long roomEnrollmentId = roomEnrollment.getRoomEnrollmentId();

        roomEnrollmentRepository.deleteById(roomEnrollmentId);
    }


    /**
     * room에 속한 모든 user를 알 수 있다.
     *
     * @param roomId
     * @return 해당 그룹의 전체 enroll 정보를 가져온다.
     */
    @Transactional
    public List<RoomEnrollmentResponseDto> findAllByRoomId(long roomId) {

        //room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        //find all roomEnrollments by room
        return roomEnrollmentRepository.findAllByRoom(room)
                .stream()
                .map(RoomEnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * user가 속한 모든 room을 알 수 있다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public List<RoomEnrollmentResponseDto> findAllByUser(long userId) {

        //user
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        //find all roomEnrollments by user
        return roomEnrollmentRepository.findAllByUser(user)
                .stream()
                .map(RoomEnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomEnrollmentResponseDto findById(long id) {

        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findById(id)
                .orElseThrow(RoomUserMismatchException::new);

        return new RoomEnrollmentResponseDto(roomEnrollment);
    }

}
