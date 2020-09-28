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
import com.together.smwu.global.aws.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomEnrollmentRepository roomEnrollmentRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private static final String DEFAULT_ROOM_IMAGE = "https://together-user-thumbnail.s3.ap-northeast-2.amazonaws.com/static/default_roomImage.jpeg";

    public RoomServiceImpl(RoomRepository roomRepository, RoomEnrollmentRepository roomEnrollmentRepository,
                           UserRepository userRepository, S3Uploader s3Uploader) {
        this.roomRepository = roomRepository;
        this.roomEnrollmentRepository = roomEnrollmentRepository;
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
    }

    public Long create(RoomRequest request, MultipartFile file, User user) throws IOException {
        String roomImageUrl = createRoomImageUrl(request, file);
        final Room room = request.toRoomEntity(roomImageUrl);
        room.addTag(request.getTags());
        enrollRoomWithMasterUser(user, room);
        roomRepository.save(room);
        return room.getId();
    }

    private String createRoomImageUrl(RoomRequest request, MultipartFile file) throws IOException {
        if (null == file) {
            return DEFAULT_ROOM_IMAGE;
        }
        String roomFileName = "room/" + request.getTitle();
        return s3Uploader.upload(file, roomFileName);
    }

    @Transactional
    public void update(Long roomId, RoomRequest request, MultipartFile file, User user) throws IOException {
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        String roomImageUrl = getRoomImageUrl(request, file, room);
        room.addTag(request.getTags());
        room.update(request.toRoomEntity(roomImageUrl));
        roomRepository.save(room);
    }

    private String getRoomImageUrl(RoomRequest request, MultipartFile file, Room room) throws IOException {
        if(null != file){
            return createRoomImageUrl(request, file);
        } else{
            return room.getImageUrl();
        }
    }

    @Transactional
    public List<RoomResponse> findByTitle(String roomTitle, User user) {
        List<Room> rooms = roomRepository.findByTitle(roomTitle);
        checkIsEmptyRoom(roomTitle, rooms);
        List<RoomDetailInfo> roomDetailInfos = rooms.stream()
                .map(roomEnrollmentRepository::getMasterUser)
                .collect(Collectors.toList());

        return RoomResponse.listFrom(roomDetailInfos);
    }

    private void checkIsEmptyRoom(String roomAttribute, List<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException(roomAttribute);
        }
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
        for (Room room : rooms) {
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
        checkIsEmptyRoom(tagName, rooms);
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
