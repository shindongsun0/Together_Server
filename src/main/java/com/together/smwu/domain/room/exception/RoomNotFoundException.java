package com.together.smwu.domain.room.exception;


import com.together.smwu.global.exception.ExpectedException;

<<<<<<< Updated upstream:src/main/java/com/together/smwu/domain/group/exception/GroupNotFoundException.java
public class GroupNotFoundException extends ExpectedException {
=======
public class RoomNotFoundException extends EntityNotFoundException {
>>>>>>> Stashed changes:src/main/java/com/together/smwu/domain/room/exception/RoomNotFoundException.java

    private static final String MESSAGE = "존재하지 않는 그룹입니다.";

    public RoomNotFoundException() {
        super(MESSAGE);
    }
<<<<<<< Updated upstream:src/main/java/com/together/smwu/domain/group/exception/GroupNotFoundException.java
=======

    public RoomNotFoundException(String target){
        super(target + TARGET_MESSAGE);
    }
>>>>>>> Stashed changes:src/main/java/com/together/smwu/domain/room/exception/RoomNotFoundException.java
}
