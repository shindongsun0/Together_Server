package com.together.smwu.domain.room.application;

import com.together.smwu.domain.room.dto.RoomRequest;
import com.together.smwu.domain.room.dto.RoomResponse;
import com.together.smwu.domain.room.dto.RoomStaticResponse;
import com.together.smwu.domain.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {

    Long create(RoomRequest request, MultipartFile imageFile, User user) throws IOException;

    void update(Long roomId, RoomRequest request, MultipartFile imageFile, User user) throws IOException;

    List<RoomResponse> findByTitle(String roomTitle, Long userId);

    RoomResponse findByRoomId(Long roomId, User user);

    List<RoomStaticResponse> findAllRooms();

    void deleteRoomById(Long roomId, User user);

    List<RoomResponse> findByTagName(String tagName, Long userId);
}
