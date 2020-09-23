package com.together.smwu.domain.roomEnrollment.application;

import com.together.smwu.domain.room.dao.RoomRepository;
import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.room.exception.RoomNotFoundException;
import com.together.smwu.domain.roomEnrollment.dao.RoomEnrollmentRepository;
import com.together.smwu.domain.roomEnrollment.domain.RoomEnrollment;
import com.together.smwu.domain.roomEnrollment.dto.RoomDetailResponse;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentRequest;
import com.together.smwu.domain.roomEnrollment.dto.RoomEnrollmentResponse;
import com.together.smwu.domain.roomEnrollment.dto.UserDetailResponse;
import com.together.smwu.domain.roomEnrollment.exception.RoomAlreadyEnrollException;
import com.together.smwu.domain.roomEnrollment.exception.RoomNotAuthorizedException;
import com.together.smwu.domain.roomEnrollment.exception.RoomUserMismatchException;
import com.together.smwu.domain.user.dao.UserRepository;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * @param request room, user, credential
     * @return true: 등록성공
     */
    @Transactional
    public Long enroll(RoomEnrollmentRequest request, User user) {
        RoomEnrollment roomEnrollment = convertToRoomEnrollment(request, user);
        checkIfAlreadyEnrolled(roomEnrollment);
        checkRoomCredential(request);
        roomEnrollmentRepository.save(roomEnrollment);
        return roomEnrollment.getRoomEnrollmentId();
    }

    private void checkIfAlreadyEnrolled(RoomEnrollment roomEnrollment) {
        boolean isExistingEnrollment = roomEnrollmentRepository
                .findByUserAndRoom(roomEnrollment.getUser().getUserId(), roomEnrollment.getRoom().getId())
                .isPresent();
        if (isExistingEnrollment) {
            throw new RoomAlreadyEnrollException();
        }
    }

    private void checkRoomCredential(RoomEnrollmentRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(RoomNotFoundException::new);

        if (room.getCredential().isEmpty()) {
            if (!isSavedCredential(request, room)) {
                throw new RoomNotAuthorizedException();
            }
        }

    }

    private boolean isSavedCredential(RoomEnrollmentRequest request, Room room) {
        return request.getCredential().equals(room.getCredential());
    }

    private RoomEnrollment convertToRoomEnrollment(RoomEnrollmentRequest request, User user) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(RoomNotFoundException::new);

        boolean isMasterUser = checkIsMasterUser(room);

        return RoomEnrollment.builder()
                .room(room)
                .user(user)
                .isMaster(isMasterUser)
                .build();
    }

    private boolean checkIsMasterUser(Room room) {
        return roomEnrollmentRepository.findAllByRoom(room).isEmpty();
    }

    /**
     * room을 삭제하거나 모두 강퇴시킨다.
     *
     * @param roomId 어떤 room
     * @param user   방장인지 확인하기 위함.
     */
    @Transactional
    public void deleteAllUsers(Long roomId, User user) {
        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findByUserAndRoom(user.getUserId(), roomId)
                .orElseThrow(RoomUserMismatchException::new);
        Room room = roomRepository.findById(roomEnrollment.getRoom().getId())
                .orElseThrow(RoomNotFoundException::new);
        if (roomEnrollment.getIsMaster()) {
            roomEnrollmentRepository.deleteAllByRoom(room);
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
    public void deleteUserFromRoom(Long roomId, User user) {
        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findByUserAndRoom(user.getUserId(), roomId)
                .orElseThrow(RoomUserMismatchException::new);
        Long roomEnrollmentId = roomEnrollment.getRoomEnrollmentId();

        roomEnrollmentRepository.deleteById(roomEnrollmentId);
    }


    /**
     * room에 속한 모든 user를 알 수 있다.
     *
     * @param roomId
     * @return 해당 그룹의 전체 enroll 정보를 가져온다.
     */
    @Transactional
    public List<UserDetailResponse> findAllByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        return roomEnrollmentRepository.findAllByRoom(room)
                .stream()
                .map(RoomEnrollment::getUser)
                .map(UserDetailResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * user가 속한 모든 room을 알 수 있다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public List<RoomDetailResponse> findAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return roomEnrollmentRepository.findRoomDetailInfosByUser(user)
                .stream()
                .map(RoomDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomEnrollmentResponse findById(Long id) {
        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findById(id)
                .orElseThrow(RoomUserMismatchException::new);

        return new RoomEnrollmentResponse(roomEnrollment);
    }

    @Override
    public RoomEnrollmentResponse findByRoomAndUser(Long userId, Long roomId) {
        RoomEnrollment roomEnrollment = roomEnrollmentRepository.findByUserAndRoom(userId, roomId)
                .orElseThrow(RoomUserMismatchException::new);
        return new RoomEnrollmentResponse(roomEnrollment);
    }

}
